package com.joeljhou.jul;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * 使用SlF4J作为Java Util Logging的日志门面
 */
@SpringBootTest
@Slf4j
class SlF4JLombkApplicationTests {

    @Test
    void contextLoads() {
        log.error("这是一条【错误】的信息");
        log.warn("这是一条【警告】的信息");
        log.info("这是一条【普通】的信息");
        log.debug("这是一条【调试】的信息");
        log.trace("这是一条【追踪】的信息");
    }

}
