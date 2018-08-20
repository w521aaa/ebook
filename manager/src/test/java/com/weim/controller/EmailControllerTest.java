package com.weim.controller;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author weim
 * @date 18-7-31
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void send() throws Exception {
        Map<String, Object> email = new HashMap<>(8);
        email.put("title","测试title");
        email.put("content","这是一个测试邮件");
        email.put("to","xxxxxxxxxxxxx");
        JSONObject jsonObject = new JSONObject(email);

//        String content = mockMvc.perform(post("/api/v1/email/send")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .accept(MediaType.APPLICATION_JSON_UTF8)
//                .content(jsonObject.toString()))
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        System.out.println("=====>" + content);

    }
}