package com.joeljhou.ehcache.basic;

import org.ehcache.CacheManager;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.impl.config.store.disk.OffHeapDiskStoreConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

/**
 * 分层选项
 */
@SpringBootTest
public class TieringOptions {

    /**
     * 堆
     */
    @Test
    public void head() {
        //缓存配置：usesConfiguredInCacheConfig
        CacheConfiguration<Long, String> usesConfiguredInCacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10, MemoryUnit.KB).offheap(10, MemoryUnit.MB))
                //设置缓存中对象及关联对象的集合最大数目为1000
                .withSizeOfMaxObjectGraph(1000)
                //设置缓存中单个对象的最大大小为1000字节
                .withSizeOfMaxObjectSize(1000, MemoryUnit.B).build();

        //缓存配置：usesDefaultSizeOfEngineConfig
        CacheConfiguration<Long, String> usesDefaultSizeOfEngineConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10, MemoryUnit.KB)).build();

        //缓存管理器
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                //默认的缓存中对象及关联对象的集合最大数目为2000
                .withDefaultSizeOfMaxObjectGraph(2000)
                //默认的缓存中单个对象的最大大小为500字节
                .withDefaultSizeOfMaxObjectSize(500, MemoryUnit.B)
                //配置缓存 usesConfiguredInCache
                .withCache("usesConfiguredInCache", usesConfiguredInCacheConfig)
                //配置缓存 usesDefaultSizeOfEngine
                .withCache("usesDefaultSizeOfEngine", usesDefaultSizeOfEngineConfig).build(true);
    }

    /**
     * 堆外层
     */
    @Test
    public void offHeap() {
        ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(10, MemoryUnit.MB);
    }

    /**
     * 磁盘层
     */
    @Test
    public void disk() {
        //1.创建持久化缓存管理器
        PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                //2.指定缓存数据写入磁盘的位置File目录“myData”文件夹中
                .with(CacheManagerBuilder.persistence(new File(getStoragePath(), "myData")))
                //预处理缓存配置
                .withCache("persistent-cache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder()
                        //3.指定缓存的磁盘大小为10MB，第三个参数为true表示缓存数据在内存中的副本也会被持久化
                        .disk(10, MemoryUnit.MB, true))).build(true);

        persistentCacheManager.close();
    }


    /**
     * 段
     */
    @Test
    public void segments() {
        String storagePath = getStoragePath();
        PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder().with(CacheManagerBuilder.persistence(new File(storagePath, "myData"))).withCache("less-segments", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().disk(10, MemoryUnit.MB))
                //1.磁盘存储被分成多个段，这些段提供并发访问，默认值16，每个段都有一个线程，可以通过下面的方法来指定段的数量
                .withService(new OffHeapDiskStoreConfiguration(2))).build(true);

        persistentCacheManager.close();
    }

    private String getStoragePath() {
        return System.getProperty("user.home") + "\\Desktop";
    }
}
