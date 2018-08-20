package com.weim.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${login.name}")
    private String name;

    @Value("${login.password}")
    private String password;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //没有session 跳转到登录页面
        if(request.getSession().getAttribute("name") == null) {
            response.sendRedirect("/admin/ebook/login");
            return false;
        }

        //有session 登录名和密码正确 通过
        String nameSession = request.getSession().getAttribute("name").toString();
        String passwordSession = request.getSession().getAttribute("password").toString();

        if(name.equals(nameSession) && password.equals(passwordSession)) {
            return true;
        } else {
            request.getSession().invalidate();
            throw new Exception("错误");
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws IOException {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
