package com.weim.schedule;

import com.weim.entity.Book;
import com.weim.entity.Title;
import com.weim.service.BookService;
import com.weim.service.TitleService;
import com.weim.utils.CommonUtils;
import com.weim.utils.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 对于已经完结的ebook 清空redis中的key
 *
 * @author weim
 * @date 18-8-1
 */

@Component
public class CleanRedisKey {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @Autowired
    private TitleService titleService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ScheduledExecutorService scheduledExecutorService = CommonUtils.getScheduledExecutorService();

    @Scheduled(cron = "0 0 1 * * ?")
    public void cleanRedisKey() {
        logger.info(CommonUtils.getNowDate() + " cleanRedisKey start");

        List<Book> bookList = bookService.findAllByEndStatus(CommonUtils.IS_END);

        logger.info("cleanRedisKey start size ==> " + bookList.size());

        for (Book book : bookList) {
            logger.info("cleanRedisKey start ==> " + book.getTitle());

            scheduledExecutorService.execute(() -> {
                List<Title> titleList = titleService.findAllByBookId(book.getId());
                for (Title title : titleList) {
                    String text = MD5Utils.encode(title.getTitle());
                    redisTemplate.delete(text);
                }

            });




        }

        logger.info(CommonUtils.getNowDate() + " cleanRedisKey end");
    }

}
