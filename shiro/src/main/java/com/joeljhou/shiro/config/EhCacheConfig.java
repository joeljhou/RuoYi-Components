package com.joeljhou.shiro.config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EhCacheConfig {

    @Value("${shiro.ehcache.config-file:classpath:ehcache/ehcache-shiro.xml}")
    private String ehcacheConfigFile;

    @Bean
    public CacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile(ehcacheConfigFile);
        return ehCacheManager;
    }
}
