package com.joeljhou.ehcache.config;

import lombok.RequiredArgsConstructor;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.ExpiryPolicy;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static org.ehcache.config.builders.CacheConfigurationBuilder.newCacheConfigurationBuilder;
import static org.ehcache.config.builders.ResourcePoolsBuilder.newResourcePoolsBuilder;

@Configuration
@EnableCaching /*支持spring的本地缓存注解*/
@RequiredArgsConstructor
public class EhcacheConfig {
    private final EhcacheConfiguration ehcacheConfiguration;

    @Bean(name = "ehCacheManager")
    public CacheManager getCacheManager() {
        return createCache("ehCache", ehcacheConfiguration.getTtlInSeconds());
    }

    private CacheManager createCache(String defaultCacheName, Long ttlInSeconds) {
        //资源池生成器配置持久化
        ResourcePoolsBuilder poolsBuilder = newResourcePoolsBuilder()
                //堆，速度最快（堆上只允许 10 个条目）
                .heap(ehcacheConfiguration.getHeap(), EntryUnit.ENTRIES)
                //堆外，速度介于heap和disk之间（只允许 10 MB）
                .offheap(ehcacheConfiguration.getOffHeap(), MemoryUnit.MB)
                //磁盘，速度最慢，但是可以存储大量数据
                .disk(ehcacheConfiguration.getDisk(), MemoryUnit.MB, true);

        //设置永不过期（ttlInSeconds < 0 or ttlInSeconds==null）
        ExpiryPolicy<Object, Object> expiryPolicy = ExpiryPolicyBuilder.noExpiration();
        if (ttlInSeconds != null && ttlInSeconds >= 0) {
            expiryPolicy = ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ttlInSeconds));
        }

        return CacheManagerBuilder.newCacheManagerBuilder()
                //指定缓存数据写入磁盘的位置
                .with(CacheManagerBuilder.persistence(ehcacheConfiguration.getDiskDir())).withCache(defaultCacheName,
                        //设置缓存键值对类型(注意：指定Object.class会存在序列化问题)
                        newCacheConfigurationBuilder(String.class, String.class, poolsBuilder)
                                //设置永不过期
                                .withExpiry(expiryPolicy)).build(true);
    }

}
