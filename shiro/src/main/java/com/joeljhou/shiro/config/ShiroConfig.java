package com.joeljhou.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.joeljhou.shiro.constant.ShiroConstants;
import com.joeljhou.shiro.realm.UserRealm;
import com.joeljhou.shiro.web.filter.captcha.CaptchaValidateFilter;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.SimpleSessionFactory;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限配置加载
 */
@Configuration
@RequiredArgsConstructor
public class ShiroConfig {


    /**
     * 登录地址
     */
    @Value("${shiro.user.loginUrl:/login}")
    private String loginUrl;

    /**
     * 权限认证失败地址
     */
    @Value("${shiro.user.unauthorizedUrl:/unauthorized}")
    private String unauthorizedUrl;

    /**
     * Session超时时间，单位为分钟（默认30分钟）
     */
    @Value("${shiro.session.expireTime:30}")
    private int expireTime;

    private final CacheManager ehCacheManager;

    /**
     * 自定义Realm
     */
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        // 设置缓存管理器
        userRealm.setAuthenticationCacheName(ShiroConstants.SYS_AUTH_CACHE);
        userRealm.setCacheManager(ehCacheManager);
        return userRealm;
    }

    /**
     * 会话管理器
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 加入缓存管理器
        sessionManager.setCacheManager(ehCacheManager);
        // 删除过期的session
        sessionManager.setDeleteInvalidSessions(true);
        // 设置全局session超时时间（单位为毫秒）
        sessionManager.setGlobalSessionTimeout(expireTime * 60 * 1000);
        // 去掉 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        // 是否定时检查session
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 设置会话验证间隔时间（单位为毫秒）
        sessionManager.setSessionValidationInterval(3600000);  // 1 hour
        // 设置会话验证调度器，默认就是使用ExecutorServiceSessionValidationScheduler
        sessionManager.setSessionValidationScheduler(new ExecutorServiceSessionValidationScheduler());
        // 设置SessionDAO，提供缓存级别的会话管理
        sessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());
        // 设置sessionFactory
        sessionManager.setSessionFactory(new SimpleSessionFactory());
        return sessionManager;
    }

    /**
     * 安全管理器
     */
    @Bean
    public DefaultSecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(userRealm);
        // 注入缓存管理
        securityManager.setCacheManager(ehCacheManager);
        // 注入会话管理器
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * Shiro过滤链配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultSecurityManager securityManager, ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 身份认证失败，则跳转到登录页面的配置
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 权限认证失败，则跳转到指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        // Shiro连接约束配置，即过滤链的定义
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());
        // 自定义过滤器
        Map<String, Filter> filters = shiroCustomFilterDefinition();
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    /**
     * 过滤链的定义
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition shiroFilterFactoryBean = new DefaultShiroFilterChainDefinition();
        // 对静态资源设置匿名访问
        shiroFilterFactoryBean.addPathDefinition("/html/**", "anon");
        shiroFilterFactoryBean.addPathDefinition("/css/**", "anon");
        shiroFilterFactoryBean.addPathDefinition("/docs/**", "anon");
        shiroFilterFactoryBean.addPathDefinition("/fonts/**", "anon");
        shiroFilterFactoryBean.addPathDefinition("/img/**", "anon");
        shiroFilterFactoryBean.addPathDefinition("/ajax/**", "anon");
        shiroFilterFactoryBean.addPathDefinition("/js/**", "anon");
        // 验证码过滤
        shiroFilterFactoryBean.addPathDefinition("/captcha/captchaImage**", "anon");
        // 退出 logout地址，shiro去清除session
        shiroFilterFactoryBean.addPathDefinition("/logout", "logout");
        // 不需要拦截的访问
        shiroFilterFactoryBean.addPathDefinition("/login", "anon,captchaValidate");
        // 注册相关
        shiroFilterFactoryBean.addPathDefinition("/register", "anon,captchaValidate");
        // 系统权限列表
        // filterChainDefinitionMap.putAll(SpringUtils.getBean(IMenuService.class).selectPermsAll());
        // 所有请求需要认证
        shiroFilterFactoryBean.addPathDefinition("/**", "user");
        return shiroFilterFactoryBean;
    }

    /**
     * 自定义过滤器
     */
    private Map<String, Filter> shiroCustomFilterDefinition() {
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("captchaValidate", captchaValidateFilter());
        return filters;
    }

    /**
     * 自定义验证码过滤器
     */
    public CaptchaValidateFilter captchaValidateFilter() {
        CaptchaValidateFilter captchaValidateFilter = new CaptchaValidateFilter();
        return captchaValidateFilter;
    }


    /**
     * thymeleaf模板引擎和shiro框架的整合
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
