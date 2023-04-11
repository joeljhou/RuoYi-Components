package com.joeljhou.jul;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Java Util Logging代码示例
 */
@SpringBootTest
class JulApplicationTests {

    //使用Java默认的日志记录器
    private static final Logger logger = Logger.getLogger(JulApplicationTests.class.getName());

    @Test
    void contextLoads() throws IOException {
        //1. 创建Logger对象
        Logger logger = Logger.getLogger(JulApplicationTests.class.getName());

        //2. 将Logger Level（记录器级别）设置为ALL
        logger.setLevel(Level.ALL);

        //3. 添加文件处理程序
        FileHandler fileHandler = new FileHandler("/path/to/file.log");
        logger.addHandler(fileHandler);

        //4. 设置格式化程序
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);

        //5. 日志消息
        logger.log(Level.INFO, "This is an information message.");
        logger.log(Level.WARNING, "This is a warning message.");
        logger.log(Level.SEVERE, "This is a severe message.");

        //6. 关闭记录器，逐一关闭Logger对象的所有Handler，以释放资源
        for (Handler handler : logger.getHandlers()) {
            handler.close();
        }
    }

}
