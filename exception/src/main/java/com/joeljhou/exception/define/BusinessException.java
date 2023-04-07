package com.joeljhou.exception.define;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常（继承自 ApplicationException）
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends ApplicationException {
    private static final long serialVersionUID = 1L;


}