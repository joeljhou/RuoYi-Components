package com.joeljhou.ehcache.basic;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.time.Duration;

import static org.ehcache.config.builders.CacheConfigurationBuilder.newCacheConfigurationBuilder;
import static org.ehcache.config.builders.CacheManagerBuilder.newCacheManagerBuilder;
import static org.ehcache.config.builders.ResourcePoolsBuilder.heap;
import static org.ehcache.config.builders.ResourcePoolsBuilder.newResourcePoolsBuilder;

/**
 * 入门：这段代码使用了Ehcache 3的API创建和管理缓存
 * https://www.ehcache.org/documentation/3.10/getting-started.html
 */
@SpringBootTest
@Slf4j
public class EhcacheStarted {

    /**
     * 程序化配置
     */
    @Test
    public void lifeCycle() {
        //1.使用缓存构建器 创建 缓存管理器（它负责创建、配置和关闭缓存）
        CacheManager cacheManager = newCacheManagerBuilder()
                //2.预先配置缓存
                .withCache("preConfigured",
                        //缓存的 key为Long类型，value 为String类型
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                                //最大容量为10个条目，并将其存储在堆内存中
                                ResourcePoolsBuilder.heap(10)))
                //3.构建缓存管理器
                .build();

        //4.初始化缓存管理器
        cacheManager.init();

        //5.获取缓存“preConfigured”
        Cache<Long, String> preConfigured = cacheManager.getCache("preConfigured", Long.class, String.class);

        //6.通过缓存管理器创建缓存“myCache”
        Cache<Long, String> myCache = cacheManager.createCache("myCache",
                //缓存的 key为Long类型，value 为String类型
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                        //最大容量为10个条目，并将其存储在堆内存中
                        ResourcePoolsBuilder.heap(10)));

        //7.向缓存中添加数据
        myCache.put(1L, "da one!");

        //8.从缓存中获取数据
        String value = myCache.get(1L);
        log.info("Retrieved '{}'", value);

        //9.删除缓存“preConfigured”
        cacheManager.removeCache("preConfigured");

        //10.关闭缓存管理器
        cacheManager.close();
    }

    /**
     * 较短的版本
     */
    @Test
    public void shorterVersion() {
        //1.try-with-resources 自动关闭流(Java 7及以后版本的一个特性)
        try (CacheManager cacheManager = newCacheManagerBuilder()
                //预先配置缓存
                .withCache("preConfigured",
                        //2.对所有构建器使用静态导入
                        newCacheConfigurationBuilder(Long.class, String.class,
                                //最大容量为 10 条目，并将其存储在堆内存中
                                heap(10)))
                //3.构建缓存管理器并初始化
                .build(true)) {
        }
    }


    /**
     * 存储层三层结构
     * 缓存使用堆、堆外和磁盘资源池
     */
    @Test
    public void storageTiers() {
        PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                //1.指定缓存数据写入磁盘的位置File目录“myData”文件夹中
                .with(CacheManagerBuilder.persistence(new File(getStoragePath(), "myData")))
                //创建名为“threeTieredCache”的三层缓存
                .withCache("threeTieredCache",
                        //设置缓存键值对类型
                        newCacheConfigurationBuilder(Long.class, String.class, newResourcePoolsBuilder()
                                //2.堆，速度最快（堆上只允许 10 个条目）
                                .heap(10, EntryUnit.ENTRIES)
                                //3.堆外，速度介于heap和disk之间（只允许 10 MB）
                                .offheap(1, MemoryUnit.MB)
                                //4.磁盘，速度最慢，但是可以存储大量数据
                                .disk(20, MemoryUnit.MB, true))).build(true);

        Cache<Long, String> threeTieredCache = persistentCacheManager.getCache("threeTieredCache", Long.class, String.class);
        //5.向缓存中添加数据
        threeTieredCache.put(1L, "stillAvailableAfterRestart");

        persistentCacheManager.close();
    }

    private String getStoragePath() {
        return System.getProperty("user.home") + "\\Desktop";
    }

    /**
     * 缓存过期
     */
    @Test
    public void cacheExpiration() {
        //1.定义缓存配置
        CacheConfiguration<Long, String> cacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                        //使用ResourcePoolsBuilder创建资源池
                        ResourcePoolsBuilder.heap(100))
                //2.设置Expiry缓存过期策略（Duration.ZERO-立即过期，Duration.INFINITE-永不过期）
                //Duration.ofSeconds(20) 表示 20s后过期
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(20))).build();
    }

}
