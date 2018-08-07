package com.weim.schedule;

import com.weim.entity.Content;
import com.weim.entity.Title;
import com.weim.service.ContentService;
import com.weim.service.TitleService;
import com.weim.utils.CommonUtils;
import com.weim.utils.JsoupUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author weim
 * @date 18-7-27
 */

@Component
public class FetchContent {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContentService contentService;

    @Autowired
    private TitleService titleService;

    private ScheduledExecutorService scheduledExecutorService = CommonUtils.getScheduledExecutorService();

    @Scheduled(cron = "0 0 4 * * ?")
    public void fetchContent() {

        logger.info(CommonUtils.getNowDate() + " fetchContent start");

        List<Title> titleList = titleService.findAllStatus(CommonUtils.IS_NOT_DOWNLOAD);

        logger.info("fetchContent start size=" + titleList.size());

        for (Title title : titleList) {
            scheduledExecutorService.execute(() -> {
                //获取内容的url
                String url = title.getUrl();
                try {
                    Content content = new Content();

                    content.setTitleId(title.getId());
                    content.setTitle(title.getTitle());

                    String htmlString = JsoupUtils.getHtmlString(url, "GBK", "div#content");
                    content.setContent(htmlString);

                    content.setStatus(CommonUtils.IS_DOWNLOAD);
                    content.setCreateDate(new Date());
                    contentService.saveContent(content);

                    //下载内容完成之后 更新title状态
                    title.setStatus(CommonUtils.IS_DOWNLOAD);
                    titleService.saveTitle(title);

                } catch (IOException e) {
                    logger.error(CommonUtils.getNowDate() + " FetchContentSchedule == fetchContent ==" + e.getMessage());
                }
            });
        }
        logger.info(CommonUtils.getNowDate() + " fetchContent end");
    }
}
