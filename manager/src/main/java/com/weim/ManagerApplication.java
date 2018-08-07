package com.weim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author weim
 * @date 18-7-26
 */

@SpringBootApplication
@EnableScheduling
public class ManagerApplication {

        public static void main(String[] args) {
            SpringApplication.run(ManagerApplication.class, args);
        }
}
