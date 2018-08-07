package com.weim.controller;

import com.weim.entity.Message;
import com.weim.service.MessageService;
import com.weim.utils.CommonUtils;
import com.weim.utils.ResObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author weim
 * @date 18-8-1
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/admin/ebook/message")
    public String findAllMessage(Model model) {

        List<Message> messageList = messageService.findAll();

        model.addAttribute("messageList", messageList);
        return "message";
    }

    @ResponseBody
    @PostMapping("/admin/ebook/message/{messageId}")
    public ResObject dealMessage(@PathVariable String messageId) {

        Message message = messageService.updateMessage(messageId);

        //TODO 发邮件通知

        return new ResObject(message);
    }

}
