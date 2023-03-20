package com.joeljhou.shiro.web.filter.captcha;

import com.google.code.kaptcha.Constants;
import com.joeljhou.shiro.constant.ShiroConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 验证码过滤器，用于验证基于图片的验证码
 */
public class CaptchaValidateFilter extends AccessControlFilter {

    /**
     * 验证通过返回true，否则返回false，并执行onAccessDenied方法
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 不是表单提交 允许访问
        if (!"POST".equals(httpServletRequest.getMethod())) {
            return true;
        }
        // 请求参数中获取验证码
        String captcha = request.getParameter("validateCode");
        // Session中获取验证码
        HttpSession session = httpServletRequest.getSession();
        String sessionCaptcha = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        // 验证码清除，防止多次使用。
        session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
        // 比较验证码
        if (StringUtils.isEmpty(captcha) || StringUtils.isEmpty(sessionCaptcha) || !captcha.equalsIgnoreCase(sessionCaptcha)) {
            return false;
        }
        return true;
    }

    /**
     * onAccessDenied方法返回true，表示继续执行后续的过滤器，返回false，表示不再执行后续的过滤器
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        request.setAttribute(ShiroConstants.CURRENT_CAPTCHA, ShiroConstants.CAPTCHA_ERROR);
        return true;
    }

}
