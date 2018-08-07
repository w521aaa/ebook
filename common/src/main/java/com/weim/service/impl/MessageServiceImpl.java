package com.weim.service.impl;

import com.weim.entity.Message;
import com.weim.repository.MessageRepository;
import com.weim.service.MessageService;
import com.weim.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author weim
 * @date 18-8-1
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message addMessage(Message message) {
        return messageRepository.saveAndFlush(message);
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll(new Sort(Sort.Direction.ASC, "status"));
    }

    @Override
    public Message updateMessage(String messageId) {
        //确保存在
        Message message = messageRepository.findById(messageId).get();
        message.setStatus(CommonUtils.IS_DEAL);
        return messageRepository.saveAndFlush(message);
    }
}
