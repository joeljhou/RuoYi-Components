package com.joeljhou.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class SysLoginController {

    /**
     * 是否开启记住我功能
     */
    @Value("${shiro.rememberMe.enabled: false}")
    private boolean rememberMe;

    //登录页面跳转
    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        if (username == null || password == null) {
            model.addAttribute("msg", "请输入用户名或密码");
            return "login";
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return "index";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "用户或密码错误");
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "验证码错误");
        } catch (AuthenticationException e) {
            model.addAttribute("msg", Objects.isNull(e.getMessage()) ? "未知错误,请联系管理员" : e.getMessage());
        }
        return "login";
    }

}
