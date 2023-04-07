package com.joeljhou.exception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 全局异常处理
 */
@SpringBootApplication
public class ExceptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExceptionApplication.class, args);
    }

}
