package com.joeljhou.exception.handler;

import com.joeljhou.exception.define.ApplicationException;
import com.joeljhou.exception.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理绑定异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleBindException(BindException e) {
        String message = e.getFieldError().getDefaultMessage();
        return R.failed(message);
    }

    /**
     * 处理校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return R.failed(message);
    }


    /**
     * 捕获自定义异常 ApplicationException
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApplicationException.class)
    public R<Void> handleApplicationException(ApplicationException e) {
        return R.failed(e.getCode(), e.getMessage());
    }

    /**
     * 捕获全局异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception e) {
        //可根据需要选择打印日志或发送邮件通知等
        return R.failed(e.getMessage());
    }
}
