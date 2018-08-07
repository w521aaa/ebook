package com.weim.controller;

import com.weim.utils.CommonUtils;
import com.weim.utils.ResObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author weim
 * @date 18-8-1
 */
@Controller
public class TestController {

    private ScheduledExecutorService executorService = CommonUtils.getScheduledExecutorService();

    private ExecutorService service = Executors.newScheduledThreadPool(2);


    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/test/a")
    public String test1() {

        service.execute(() -> {
            System.out.println("1111111111111111111111111111111");
            try {
                my("test1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("2222222222222222222222222222222");
        });

//        new Thread(() -> {
//            System.out.println("1111111111111111111111111111111");
//
//            try {
//                my("test2");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            System.out.println("2222222222222222222222222222222");
//        }).start();

        return "success";
    }

    @ResponseBody
    @GetMapping("/test/b")
    public ResObject test2() {

        service.execute(() -> {
            System.out.println("3333333333333333333333333333333333");
            try {
                my("test3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("444444444444444444444444444444444444");
        });

//        new Thread(() -> {
//            System.out.println("3333333333333333333333333333333333");
//
//            try {
//                my("test4");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            System.out.println("444444444444444444444444444444444444");
//        }).start();

        return new ResObject("deng dai");
    }


    private void my(String str) throws InterruptedException {

        Thread.sleep(30*1000);

        System.out.println("    " +str + "     aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

    }



}
