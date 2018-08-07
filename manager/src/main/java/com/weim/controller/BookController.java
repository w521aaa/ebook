package com.weim.controller;

import com.weim.entity.Book;
import com.weim.entity.Content;
import com.weim.entity.Title;
import com.weim.service.BookService;
import com.weim.service.ContentService;
import com.weim.service.EmailService;
import com.weim.service.TitleService;
import com.weim.utils.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author weim
 */
@Controller
public class BookController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @Autowired
    private FetchAllUtils fetchAllUtils;

    @Value("${download.ebook.search.website.url}")
    private String remoteSearchWebsite;

    private ExecutorService executorService = new ScheduledThreadPoolExecutor(2);

    @GetMapping("/admin/ebook")
    public String toAdmin() {

        return "login";
    }

    @GetMapping("/admin/ebook/books")
    public String getBookList(Model model) {

        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);
        return "book";
    }

    @ResponseBody
    @PostMapping("/admin/ebook/books/{bookId}/downloadImage")
    public ResObject downloadImage(@PathVariable String bookId) {

        fetchAllUtils.fetchBookImage(bookService.findBookById(bookId));

        return new ResObject();
    }

    @PostMapping("/admin/ebook/books/search")
    public String searchBooksRemote(@RequestParam String title, Model model) throws IOException {

        String url = remoteSearchWebsite + URLEncoder.encode(title,"GBK");
        System.out.println("url>>"+url);

        //爬去搜索之后页面   获取body中的html
        String getRemoteHtml = JsoupUtils.getHtmlString(url, "GBK", "body");
        //转化为Document
        Document document = Jsoup.parse(getRemoteHtml);

        //没有div#tips  则说明查找唯一 直接爬取内容
        if(document.select("div#tips").size() == 0) {

            executorService.execute(() -> {
                dealHtmlString(document);
            });

            model.addAttribute("msg", "正在后台默默执行");

            return "success";
        } else {
            //进入选择页面  拿到查到所有书的列表  输入url爬取内容
            model.addAttribute("fetchHtml", document.select("div#content table").html());
            return "choice";
        }
    }

    @ResponseBody
    @PostMapping("/admin/ebook/books/fetch")
    public ResObject getBooksRemote(@RequestParam String url) throws IOException {
        String getRemoteHtml = JsoupUtils.getHtmlString(url, "GBK", "body");

        executorService.execute(() -> {
            dealHtmlString(Jsoup.parse(getRemoteHtml));
        });

        return new ResObject(0, "正在后台默默执行");
    }

    @ResponseBody
    @DeleteMapping("/admin/ebook/books/{bookId}")
    public ResObject deleteBook(@PathVariable String bookId) throws IOException {

        bookService.deleteBook(bookId);

        return new ResObject();
    }


    /**
     * 处理 获取内容
     * 直接保存到数据库中
     * @param document
     */
    private void dealHtmlString(Document document) {

        Book book = fetchAllUtils.fetchBook(document);

        fetchAllUtils.fetchTitle(document, book, true);
    }

}
