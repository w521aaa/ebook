package com.weim.schedule;

import com.weim.entity.Book;
import com.weim.service.BookService;
import com.weim.utils.CommonUtils;
import com.weim.utils.FetchAllUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时更新没有下载的书本图片
 * @author weim
 * @date 18-7-27
 */

@Component
public class FetchBookImage {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @Autowired
    private FetchAllUtils fetchAllUtils;

    @Scheduled(cron = "0 0 3 * * ?")
    public void fetchBookImage() {

        logger.info(CommonUtils.getNowDate() + " fetchBookImage start");

        List<Book> bookList = bookService.findAllByStatus(CommonUtils.IS_NOT_DOWNLOAD);

        logger.info("fetchBookImage start size ==> " + bookList.size());


        for (Book book : bookList) {
            logger.info("fetchBookImage start ==> " + book.getTitle());
            fetchAllUtils.fetchBookImage(book);
        }
        logger.info(CommonUtils.getNowDate() + " fetchBookImage end");
    }
}
