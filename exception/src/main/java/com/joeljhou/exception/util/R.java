package com.joeljhou.exception.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 响应信息主体
 * 异常码规范：200-成功，400-客户端错误，500-服务端错误
 *
 * @param <T>
 */
@ToString
@NoArgsConstructor
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;

    @Getter
    @Setter
    private T data;

    public static final int SUCCESS_CODE = 200;
    public static final int FAILED_CODE = 500;

    public static <T> R<T> success() {
        return restResult(null, SUCCESS_CODE, "success");
    }

    public static <T> R<T> success(T data) {
        return restResult(data, SUCCESS_CODE, "success");
    }

    public static <T> R<T> success(T data, String msg) {
        return restResult(data, SUCCESS_CODE, msg);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, FAILED_CODE, msg);
    }

    public static <T> R<T> failed(int code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public static boolean isSucceed(R r) {
        return r.getCode() == SUCCESS_CODE;
    }

    public static boolean isFailed(R r) {
        return r.getCode() != SUCCESS_CODE;
    }

}
