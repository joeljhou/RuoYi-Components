package com.joeljhou.jul.format;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);

    @Override
    public String format(LogRecord record) {
        StringBuffer sb = new StringBuffer();
        // 时间戳
        sb.append(DATE_FORMAT.format(new Date(record.getMillis())));
        // 日志级别
        String level = record.getLevel().getName();
        sb.append(String.format("%10s", level));
        // 进程ID（PID）
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        sb.append(String.format("%8s", pid)).append(" --- ");
        // 线程名
        sb.append("[           ").append(record.getThreadID()).append("] ");
        // 日志源
        sb.append(record.getSourceClassName()).append(".").append(record.getSourceMethodName());
        // 日志消息
        sb.append("     : ").append(record.getMessage());
        // 异常信息
        Throwable throwable = record.getThrown();
        if (throwable != null) {
            sb.append(LINE_SEPARATOR)
                    .append(throwable.toString()).append(LINE_SEPARATOR);
            StackTraceElement[] stackTrace = throwable.getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                sb.append("\tat ")
                        .append(stackTraceElement.getClassName()).append(".")
                        .append(stackTraceElement.getMethodName()).append("(")
                        .append(stackTraceElement.getFileName()).append(":")
                        .append(stackTraceElement.getLineNumber()).append(")").append(LINE_SEPARATOR);
            }
        }
        sb.append(LINE_SEPARATOR);
        return sb.toString();
    }
}
