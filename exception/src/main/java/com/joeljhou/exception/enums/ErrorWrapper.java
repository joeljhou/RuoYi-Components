package com.joeljhou.exception.enums;

import com.joeljhou.exception.define.ApplicationException;
import com.joeljhou.exception.util.R;

public interface ErrorWrapper {

    /**
     * 获取个性化的错误消息
     *
     * @param args 错误消息参数
     * @return 个性化的错误消息
     */
    String getMessage(Object... args);

    /**
     * 抛出指定异常
     *
     * @param args 异常参数
     * @return ApplicationException
     */
    ApplicationException exception(Object... args);

    /**
     * 返回标准的失败响应 R
     *
     * @return R
     */
    R response();

}
