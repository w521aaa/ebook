package com.weim.service.impl;

import com.weim.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author weim
 * @date 18-7-31
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void send(SimpleMailMessage simpleMailMessage) {
        javaMailSender.send(simpleMailMessage);
    }
}
