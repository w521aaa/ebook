package com.weim.utils;

import com.weim.entity.Book;
import com.weim.entity.Content;
import com.weim.entity.Title;
import com.weim.service.BookService;
import com.weim.service.ContentService;
import com.weim.service.EmailService;
import com.weim.service.TitleService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author weim
 * @date 18-8-2
 */

@Component
public class FetchAllUtils {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @Autowired
    private TitleService titleService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Value("${download.ebook.website.url}")
    private String remoteWebsite;

    @Value("${local.image.default.url}")
    private String localImage;

    @Value("${spring.mail.from}")
    private String from;

    @Value("${spring.mail.to}")
    private String to;


    private ScheduledExecutorService scheduledExecutorService = CommonUtils.getScheduledExecutorService();

    /**
     * 爬取书的内容
     */
    public Book fetchBook(Document document) {
        //处理 获取内容
        Book book = new Book();
        //书名
        book.setTitle(document.select("div#info h1").text());
        //作者
        book.setAuthor(document.select("div#info p").get(0).text());
        //书本远程图片url
        book.setImage(remoteWebsite + document.select("div#fmimg img").attr("src"));
        //书本URL
        String href = document.select("div#list a").get(0).attr("href");
        String bookUrl = href.substring(0,href.lastIndexOf("/")+1);
        book.setUrl(remoteWebsite + bookUrl);

        //书本本地默认图片
        book.setLocalImage(localImage);

        book.setStatus(CommonUtils.IS_NOT_DOWNLOAD);
        //保存book
        book = bookService.saveBook(book);

        fetchBookImage(book);

        logger.info("book ==>" + book);
        return book;
    }

    /**
     * 爬取书的图片
     */
    public void fetchBookImage(Book book) {

        logger.info("fetchBookImage ==>" + book.getImage());

        scheduledExecutorService.execute(() -> {
            String url = book.getImage();
            try {
                String localUrlPath = JsoupUtils.downloadImage(url,"");
                book.setLocalImage(localUrlPath);
                book.setStatus(CommonUtils.IS_DOWNLOAD);
                bookService.saveBook(book);
            } catch (Exception e) {
                logger.error(CommonUtils.getNowDate() + " fetchBookImage ==" + e.getMessage());
            }
        });
    }

    /**
     * 爬取所有章节
     */
    public void fetchTitle(Document document, Book book, Boolean sendEmail) {
        //章节 链接  以及章节名字
        Elements lists = document.select("div#list a");

        CountDownLatch countDownLatch = new CountDownLatch(lists.size());

        logger.info("fetchTitle == > " + lists.size());

        for(int index=0; index < lists.size(); index++) {

            Element list = lists.get(index);
            final int sort = index;

            //需要判断是否已经下载了该章节 下载了 continue 并减少countDownLatch
            String text = MD5Utils.encode(list.text());
            if(redisTemplate.hasKey(text)) {
                countDownLatch.countDown();
                continue;
            }

            //保存title
            scheduledExecutorService.execute(() -> {

                Title titleTemp = new Title();
                titleTemp.setBookId(book.getId());
                titleTemp.setUrl(remoteWebsite + list.attr("href"));
                titleTemp.setTitle(list.text());
                titleTemp.setSort(sort);
                titleTemp.setStatus(CommonUtils.IS_NOT_DOWNLOAD);
                titleTemp = titleService.saveTitle(titleTemp);

                //爬取内容
                fetchContent(titleTemp, countDownLatch);

            });
        }

        //等待下载完成之后
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error( CommonUtils.getNowDate() + "  countDownLatch 未知错误 " + e.getMessage());
        }

        //回收下垃圾
        System.gc();

        if(sendEmail) {
            //发送邮件  通知下载完成了
            sendEmail(book.getTitle());
        }
    }

    public void sendEmail(String title) {

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(from);
        email.setSubject(title + "下载完成了");
        email.setText(String.format("你选择的小说<%s>下载完成了,请进入查看", title));
        email.setTo(to);
        emailService.send(email);

        logger.info("\ntime:"+CommonUtils.getNowDate()
                +"\nFrom:"+from
                +"\nTo:"+to
                +"\ntitle:"+email.getSubject()
                +"\ncontent:"+email.getText());
    }

    /**
     * 爬取章节的内容
     */
    public void fetchContent(Title title, CountDownLatch countDownLatch) {

        try {
            Content content = new Content();
            content.setTitleId(title.getId());
            content.setTitle(title.getTitle());

            String htmlString = JsoupUtils.getHtmlString(title.getUrl(), "GBK", "div#content");
            content.setContent(htmlString);

            content.setStatus(CommonUtils.IS_DOWNLOAD);
            content.setCreateDate(new Date());

            contentService.saveContent(content);

            //下载内容完成之后 更新title状态
            title.setStatus(CommonUtils.IS_DOWNLOAD);
            title.setCreateDate(new Date());
            titleService.saveTitle(title);

            //写入redis中 后续判断
            String text = MD5Utils.encode(title.getTitle());
            redisTemplate.opsForValue().set(text,title.getTitle());

        } catch (IOException e) {
            logger.error(CommonUtils.getNowDate() + "  爬取章节的内容错误 ==> " + e.getMessage());
        }

        countDownLatch.countDown();
    }

}
