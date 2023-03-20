package com.joeljhou.ehcache.xml;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.xml.XmlConfiguration;
import org.ehcache.xml.multi.XmlMultiConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.stream.Collectors;

import static org.ehcache.config.builders.CacheManagerBuilder.newCacheManager;

@SpringBootTest
@Slf4j
public class BasicXML {

    @Test
    public void templateXml() {
        Configuration xmlConfig = new XmlConfiguration(BasicXML.class.getResource("/config/template-sample.xml.xml"));
        try (CacheManager cacheManager = newCacheManager(xmlConfig)) {
            cacheManager.init();

            Cache<Long, String> basicCache = cacheManager.getCache("basicCache", Long.class, String.class);
            basicCache.put(1L, "da one!");
            String value = basicCache.get(1L);
            log.info("Retrieved '{}'", value);
        }
    }

    @Test
    public void MultipleXml() {
        XmlMultiConfiguration multipleConfiguration = XmlMultiConfiguration.from(getClass().getResource("/config/multiple-managers.xml")).build();
        Configuration fooConfiguration = multipleConfiguration.configuration("foo-manager");
        try (CacheManager cacheManager = newCacheManager(fooConfiguration)) {
            cacheManager.init();

            Cache<String, String> fooCache = cacheManager.getCache("foo", String.class, String.class);
            fooCache.put("foo", "bar");
            String value = fooCache.get("foo");
            log.info("Retrieved '{}'", value);
        }
    }


    @Test
    public void MultipleVariants() {
        XmlMultiConfiguration variantConfiguration = XmlMultiConfiguration.from(getClass().getResource("/config/multiple-variants.xml")).build();

        Configuration fooConfiguration = variantConfiguration.configuration("foo-manager", "offheap");
    }
}
