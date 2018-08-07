package com.weim.schedule;

import com.weim.entity.Book;
import com.weim.service.BookService;
import com.weim.utils.CommonUtils;
import com.weim.utils.FetchAllUtils;
import com.weim.utils.JsoupUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 定时更新书本的内容  未完结的ebook
 * @author weim
 * @date 18-8-1
 */
@Component
public class FetchNewContent {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @Autowired
    private FetchAllUtils fetchAllUtils;

    @Scheduled(cron = "0 0 5 * * ?")
    public void fetchNewContent() throws IOException {
        logger.info(CommonUtils.getNowDate() + " fetchNewContent start");

        //查找所有未完结的图书
        List<Book> bookList = bookService.findAllByEndStatus(CommonUtils.IS_NOT_END);

        logger.info(" fetchNewContent start size==>" + bookList.size());

        for(Book book : bookList) {
            //ebook的链接
            String url = book.getUrl();

            String getHtml = JsoupUtils.getHtmlString(url, "GBK", "body");

            fetchAllUtils.fetchTitle(Jsoup.parse(getHtml), book, false);
        }

        logger.info(CommonUtils.getNowDate() + " fetchNewContent end");
    }

}
