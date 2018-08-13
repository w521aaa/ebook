package com.weim.controller;

import com.weim.entity.Book;
import com.weim.entity.Content;
import com.weim.entity.Title;
import com.weim.service.BookService;
import com.weim.service.ContentService;
import com.weim.service.TitleService;
import com.weim.utils.CommonUtils;
import com.weim.utils.JsoupUtils;
import com.weim.utils.MD5Utils;
import com.weim.utils.ResObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author weim
 * @date 18-7-27
 */
@Controller
public class TitleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ScheduledExecutorService scheduledExecutorService = CommonUtils.getScheduledExecutorService();

    @Autowired
    private TitleService titleService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private BookService bookService;

    @GetMapping("/admin/ebook/titles/{bookId}")
    public String getTitlesByBookId(@PathVariable String bookId, Model model) {

        Book book = bookService.findBookById(bookId);

        List<Title> titleList = titleService.findAllByBookId(bookId);

        model.addAttribute("book", book);

        model.addAttribute("titleList", titleList);

        return "title";
    }

    @ResponseBody
    @PostMapping("/admin/ebook/titles/{titleId}/downloadContent")
    public ResObject downloadContent(@PathVariable String titleId) {

        Content content = contentService.findByTitleId(titleId);
        if(StringUtils.isEmpty(content)) {
            content = new Content();
        }
        Title title = titleService.findTitleById(titleId);

        fetchContent(content, title);

        return new ResObject();
    }

    /**
     * 一键下载
     * @param bookId
     * @return
     */
    @ResponseBody
    @PostMapping("/admin/ebook/titles/downloadAllContent/{bookId}")
    public ResObject downloadAllContent(@PathVariable String bookId) {

        List<Title> titleList = titleService.findAllByBookIdAndStatus(bookId, CommonUtils.IS_NOT_DOWNLOAD);

        logger.info("fetchContent downloadAllContent size=" + titleList.size());

        for (Title title : titleList) {
            downloadContent(title.getId());
        }

        return new ResObject();
    }



    /**
     * 爬取单个章节的内容
     */
    private void fetchContent(Content content, Title title) {
        logger.info("title fetchContent == > " + title.getTitle());

        scheduledExecutorService.execute(() -> {
            try {
                String htmlString = JsoupUtils.getHtmlString(title.getUrl(), "GBK", "div#content");
                content.setContent(htmlString);
                content.setTitle(title.getTitle());
                content.setTitleId(title.getId());
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
                logger.error(CommonUtils.getNowDate() + " title fetchContent ==" + e.getMessage());
            }
        });
    }

}
