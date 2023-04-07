package com.joeljhou.exception.enums;


import com.joeljhou.exception.define.ApplicationException;
import com.joeljhou.exception.util.R;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessErrorCode implements ErrorWrapper {

    //定义一个登录异常
    LOGIN_ERROR(20001, "登录失败，用户名 【%s】 的密码错误。"),

    ;


    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误信息
     */
    private final String message;


    /**
     * 获取个性化的错误消息
     *
     * @param args 错误消息参数
     * @return 个性化的错误消息
     */
    @Override
    public String getMessage(Object... args) {
        return String.format(message, args);
    }

    /**
     * 抛出指定异常
     *
     * @param args 异常参数
     * @return ApplicationException
     */
    @Override
    public ApplicationException exception(Object... args) {
        return new ApplicationException(code, message, args);
    }

    /**
     * 返回标准的失败响应 R
     *
     * @return R
     */
    @Override
    public R response() {
        return R.failed(code, message);
    }

}
