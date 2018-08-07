package com.weim.controller;

import com.weim.utils.CommonUtils;
import com.weim.utils.ResObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author weim
 * @date 18-7-27
 */

@RestController
public class EmailController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.from}")
    private String from;

//    @PostMapping("/api/v1/email/send")
    public ResObject send(@RequestBody Map<String,String> map) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();

            email.setFrom(from);

            email.setSubject(map.get("title"));

            email.setText(map.get("content"));

            email.setTo(map.get("to"));

            javaMailSender.send(email);

            logger.info("\ntime:"+ CommonUtils.getNowDate()
                    +"\nFrom:"+from
                    +"\nTo:"+map.get("to")
                    +"\ntitle:"+email.getSubject()
                    +"\ncontent:"+email.getText());

        } catch (Exception e) {
            return new ResObject(-1,e.getMessage());
        }
        return new ResObject();
    }

//    @PostMapping("/api/v1/email/send/all")
    public ResObject sendAll(@RequestBody Map<String,String> map) {

        try {
            SimpleMailMessage email = new SimpleMailMessage();

            email.setFrom(from);

            email.setSubject(map.get("title"));

            email.setText(map.get("content"));

            //to  以;分隔每一个邮件人
            String[] toAll = map.get("to").split(";");

            email.setTo(toAll);

            javaMailSender.send(email);

            logger.info("\ntime:"+CommonUtils.getNowDate()
                    +"\nFrom:"+from
                    +"\nTo:"+map.get("to")
                    +"\ntitle:"+email.getSubject()
                    +"\ncontent:"+email.getText());

        } catch (Exception e) {
            return new ResObject(-1,e.getMessage());
        }
        return new ResObject();
    }
}