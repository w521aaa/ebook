package com.weim.controller;

import com.weim.entity.Message;
import com.weim.service.MessageService;
import com.weim.utils.CommonUtils;
import com.weim.utils.ResObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author weim
 * @date 18-8-1
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @ResponseBody
    @PostMapping("/ebook/message")
    public ResObject addMessage(@RequestBody Message message) {

        if(StringUtils.isEmpty(message.getMessage())) {
            return new ResObject(-1, "书名不能为空");
        }

        if(StringUtils.isEmpty(message.getEmail())) {
            return new ResObject(-1, "邮箱不能为空");
        }

        message.setId(CommonUtils.getUUID());
        message.setCreateDate(new Date());
        message.setStatus(CommonUtils.IS_NOT_DEAL);

        message = messageService.addMessage(message);

        return new ResObject(message);
    }

}
