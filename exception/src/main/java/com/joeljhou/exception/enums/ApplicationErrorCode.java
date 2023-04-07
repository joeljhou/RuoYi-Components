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
public enum ApplicationErrorCode implements ErrorWrapper {

    INVALID_PARAMETER(10001, "无效的参数"),

    MISSING_PARAMETER(10002, "缺失的参数"),

    FILE_IO_EXCEPTION(10003, "文件读写异常"),

    DATABASE_EXCEPTION(10004, "数据库异常"),

    NETWORK_EXCEPTION(10005, "网络异常"),

    CONNECTION_REFUSED_EXCEPTION(10006, "连接被拒绝"),

    UNAUTHORIZED_EXCEPTION(10007, "未授权"),

    FORBIDDEN_EXCEPTION(10008, "禁止访问"),

    RESOURCE_NOT_FOUND_EXCEPTION(10009, "资源不存在"),

    UNSUPPORTED_MEDIA_TYPE_EXCEPTION(10010, "不支持的媒体类型"),

    SERVICE_UNAVAILABLE_EXCEPTION(10011, "服务不可用"),

    INTERNAL_SERVER_ERROR_EXCEPTION(10012, "服务器内部错误"),

    TIMEOUT_EXCEPTION(10013, "超时异常"),

    DUPLICATE_KEY_EXCEPTION(10014, "唯一键冲突"),

    UNSUPPORTED_OPERATION_EXCEPTION(10015, "不支持的操作"),

    SYSTEM_SERVICE_ERROR(99999, "系统服务错误"),

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
