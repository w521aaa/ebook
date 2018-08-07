package com.weim.service;

import org.springframework.mail.SimpleMailMessage;

/**
 * @author weim
 * @date 18-7-31
 */
public interface EmailService {

    void send(SimpleMailMessage simpleMailMessage);
}
