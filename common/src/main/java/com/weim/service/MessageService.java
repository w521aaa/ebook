package com.weim.service;

import com.weim.entity.Message;

import java.util.List;

/**
 * @author weim
 * @date 18-8-1
 */
public interface MessageService {
    Message addMessage(Message message);

    List<Message> findAll();

    Message updateMessage(String messageId);
}
