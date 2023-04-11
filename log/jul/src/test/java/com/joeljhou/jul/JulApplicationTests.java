package com.joeljhou.jul;

import com.joeljhou.jul.format.CustomFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Java Util Logging代码示例
 */
@SpringBootTest
class JulApplicationTests {

    //1. 创建Logger对象
    private static final Logger logger = Logger.getLogger(JulApplicationTests.class.getName());

    @Test
    void contextLoads() throws IOException {
        //2. 将Logger Level（记录器级别）设置默认的INFO
        logger.setLevel(Level.INFO);

        //3. 添加文件处理程序
        String filePath = System.getProperty("os.name").toLowerCase().contains("win") ? "D:\\path\\to\\file.log" : "/path/to/file.log";
        FileHandler fileHandler = new FileHandler(filePath);
        logger.addHandler(fileHandler);

        //4. 设置格式化程序
        fileHandler.setFormatter(new CustomFormatter());  // 使用自定义格式输出日志

        //5. 日志消息（级别由高到低）
        logger.log(Level.SEVERE, "这是一个【严重】的信息");
        logger.log(Level.WARNING, "这是一个【警告】的信息");
        logger.log(Level.INFO, "这是一个【信息】的信息");
        logger.log(Level.CONFIG, "这是一个【配置】的信息");
        logger.log(Level.FINE, "这是一个【良好】的信息");
        logger.log(Level.FINER, "这是一个【更好】的信息");
        logger.log(Level.FINEST, "这是一个【最好】的信息");
        logger.log(Level.ALL, "这是一个【所有】的信息");

        //6. 关闭记录器，逐一关闭Logger对象的所有Handler，以释放资源
        for (Handler handler : logger.getHandlers()) {
            handler.close();
        }
    }

}
