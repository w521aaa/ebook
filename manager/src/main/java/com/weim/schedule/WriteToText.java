package com.weim.schedule;

import com.weim.entity.Book;
import com.weim.entity.Title;
import com.weim.service.BookService;
import com.weim.service.ContentService;
import com.weim.service.TitleService;
import com.weim.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author weim
 * @date 18-8-10
 */
@Component
public class WriteToText {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @Autowired
    private TitleService titleService;

    @Autowired
    private ContentService contentService;

    private ScheduledExecutorService scheduledExecutorService = CommonUtils.getScheduledExecutorService();

    /**
     * 5点爬取新内容  所以要在之前写入文件  定在3点
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void writeBookToText() {

        logger.info(CommonUtils.getNowDate() + " writeBookToText start");

        List<Book> bookList = bookService.findAllByEndStatus(CommonUtils.IS_NOT_END);

        logger.info(CommonUtils.getNowDate() + " writeBookToText start bookList " + bookList.size());

        for (Book book : bookList) {

            logger.info(CommonUtils.getNowDate() + " writeBookToText start book " + book.getTitle());

            scheduledExecutorService.execute(() -> {
                String bookName = book.getTitle();

                String filePath = CommonUtils.getUplodFilePath() + "text/" + bookName + ".txt";

                try {

                    FileWriter fileWriter = new FileWriter(filePath,true);

                    //查找该本书下  新增的章节  根据时间
                    //凌晨5点爬取新内容   所以时间设置为昨天五点以后
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
                    calendar.set(Calendar.HOUR_OF_DAY, 5);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);

                    List<Title> titleList = titleService.findAllByBookIdAndCreateDate(book.getId(), calendar.getTime());

                    for (Title title : titleList) {
                        //写入标题
                        fileWriter.write("\n" + title.getTitle() + "\n");

                        //写入内容
                        String contentText = contentService.findByTitleId(title.getId()).getContent()
                                .replaceAll("<br>.*nbsp;", "")
                                .replaceAll("<br>", "")
                                .replaceAll("&nbsp;", "");
                        fileWriter.write(contentText);
                    }

                    fileWriter.flush();
                    fileWriter.close();

                } catch (IOException e) {
                    logger.error(CommonUtils.getNowDate() + " 打开文件失败 " + bookName);
                }
            });
        }

        logger.info(CommonUtils.getNowDate() + " writeBookToText end");
    }


}
