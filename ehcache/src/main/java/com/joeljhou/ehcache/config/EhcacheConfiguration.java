package com.joeljhou.ehcache.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@ConfigurationProperties("framework.cache.ehcache")
public class EhcacheConfiguration implements Serializable {

    /**
     * ehcache heap大小
     * jvm内存中缓存的key数量
     */
    private int heap;

    /**
     * ehcache off-heap大小
     * 堆外内存大小, 单位: MB
     */
    private int offHeap;

    /**
     * 磁盘持久化目录
     */
    private String diskDir;

    /**
     * ehcache disk
     * 持久化到磁盘的大小, 单位: MB
     * diskDir有效时才生效
     */
    private int disk;

    /**
     * 缓存过期时间, 单位: 秒
     */
    private Long ttlInSeconds;

    public EhcacheConfiguration() {
        heap = 1000;
        offHeap = 100;
        disk = 500;
        diskDir = System.getProperty("java.io.tmpdir");
    }

}
