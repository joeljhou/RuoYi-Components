package com.joeljhou.log4j2;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Log4j2ApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(Log4j2ApplicationTests.class);

    @Test
    void contextLoads() {
        logger.error("这是一条【错误】的信息");
        logger.warn("这是一条【警告】的信息");
        logger.info("这是一条【普通】的信息");
        logger.debug("这是一条【调试】的信息");
        logger.trace("这是一条【追踪】的信息");
    }

}
