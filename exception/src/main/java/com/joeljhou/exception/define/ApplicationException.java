package com.joeljhou.exception.define;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * 应用程序异常（继承自 RuntimeException）
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApplicationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 异常编码
     */
    private Integer code;

    /**
     * 异常消息
     */
    private String message;

    /**
     * 异常数据
     */
    private Object data;

    /**
     * 异常参数
     */
    private Object[] args;

    /**
     * 构造函数，用于抛出非编码异常，消息已经定义好了
     *
     * @param message 异常消息
     * @param args    异常消息中格式化参数列表
     */
    public ApplicationException(String message, Object... args) {
        super(String.format(message, args));
        this.args = args;
    }

    /**
     * 构造函数，使用特定的异常编码和异常消息来构造 ApplicationException
     *
     * @param code    异常编码
     * @param message 异常消息
     */
    public ApplicationException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数，使用特定的异常编码、异常消息和异常数据来构造 ApplicationException
     *
     * @param code    异常编码
     * @param message 异常消息
     * @param data    异常数据
     */
    public ApplicationException(Integer code, String message, Object data) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造函数，使用特定的异常编码、异常消息和异常参数来构造 ApplicationException
     *
     * @param code    异常编码
     * @param message 异常消息
     * @param args    异常参数
     */
    public ApplicationException(Integer code, String message, Object[] args) {
        super(String.format(message, args));
        this.code = code;
        this.message = String.format(message, args);
        this.args = args;
    }


    /**
     * 构造函数，使用特定的异常编码、异常消息、异常数据和异常参数来构造 ApplicationException
     *
     * @param code    异常编码
     * @param message 异常消息
     * @param data    异常数据
     * @param args    异常参数
     */
    public ApplicationException(Integer code, String message, Object data, Object[] args) {
        super(String.format(message, args));
        this.code = code;
        this.message = String.format(message, args);
        this.data = data;
        this.args = args;
    }

}