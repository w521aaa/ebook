package com.weim.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/admin/ebook/login")
    public String goLogin() {

        return "login";
    }

    @PostMapping("/admin/ebook/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        logger.info("name = " + request.getParameter("name"));
        logger.info("password = " + request.getParameter("password"));

        request.getSession().setAttribute("name", request.getParameter("name"));
        request.getSession().setAttribute("password", request.getParameter("password"));

        response.sendRedirect("/admin/ebook/books");
    }

}
