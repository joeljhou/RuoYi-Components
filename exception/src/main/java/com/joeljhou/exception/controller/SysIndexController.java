package com.joeljhou.exception.controller;

import com.joeljhou.exception.enums.BusinessErrorCode;
import com.joeljhou.exception.pojo.User;
import com.joeljhou.exception.util.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysIndexController {
    /**
     * 首页方法
     */
    @GetMapping("/login")
    public R<Void> login(@Validated User user) {
        //模拟用户未登录，抛出业务逻辑异常
        throw BusinessErrorCode.LOGIN_ERROR.exception(user.getUsername());
    }
}
