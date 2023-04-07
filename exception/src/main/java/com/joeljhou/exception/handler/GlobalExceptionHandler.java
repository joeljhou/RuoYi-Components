package com.joeljhou.exception.handler;

import com.joeljhou.exception.define.ApplicationException;
import com.joeljhou.exception.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常.
     * @param e the e
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleException(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        return R.failed(e.getMessage());
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
     * 处理业务校验过程中碰到的非法参数异常 该异常基本由{@link org.springframework.util.Assert}抛出
     * @param exception 参数校验异常
     * @return API返回结果对象包装后的错误输出结果
     * @see Assert#hasLength(String, String)
     * @see Assert#hasText(String, String)
     * @see Assert#isTrue(boolean, String)
     * @see Assert#isNull(Object, String)
     * @see Assert#notNull(Object, String)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public R handleIllegalArgumentException(IllegalArgumentException exception) {
        log.error("非法参数,ex = {}", exception.getMessage(), exception);
        return R.failed(exception.getMessage());
    }

    /**
     * 验证异常
     * @param exception
     * @return R
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleBodyValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        log.warn("参数绑定异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
        return R.failed(String.format("%s %s", fieldErrors.get(0).getField(), fieldErrors.get(0).getDefaultMessage()));
    }

    /**
     * 验证异常 (以form-data形式传参)
     * @param exception
     * @return R
     */
    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R bindExceptionHandler(BindException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        log.warn("参数绑定异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
        return R.failed(fieldErrors.get(0).getDefaultMessage());
    }

}
