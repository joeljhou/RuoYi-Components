package com.joeljhou.exception.enums;


import com.joeljhou.exception.define.ApplicationException;
import com.joeljhou.exception.util.MsgUtils;
import com.joeljhou.exception.util.R;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.NoSuchMessageException;

import java.text.MessageFormat;

@Getter
@AllArgsConstructor
public enum BusinessErrorCode implements ErrorWrapper {

    //定义一个登录异常
    LOGIN_ERROR(20001, "登录失败，用户名 【{0}】 的密码错误。"),

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
     * 获取国际化的错误消息
     * @param args 错误消息参数
     * @return 个性化的错误消息
     */
    @Override
    public String getMessage(Object... args) {
        try {
            return MsgUtils.getMessage(name(), args);
        } catch (NoSuchMessageException e) {
            //如果没有找到国际化的错误消息，就使用默认的错误消息
            return MessageFormat.format(message, args);
        }
    }

    /**
     * 抛出指定异常
     * @param args 异常参数
     * @return ApplicationException
     */
    @Override
    public ApplicationException exception(Object... args) {
        return new ApplicationException(code, getMessage(args));
    }

    /**
     * 返回标准的失败响应 R
     * @return R
     */
    @Override
    public R response() {
        return R.failed(code, getMessage());
    }

}
