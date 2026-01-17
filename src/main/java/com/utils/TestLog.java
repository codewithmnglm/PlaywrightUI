package com.utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class TestLog {

    public static Logger logger;

    static {
        // Set up custom formatter for cleaner logs
        logger = Logger.getLogger(TestLog.class.getName());
        logger.setUseParentHandlers(false); // Disable default handler
        
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return String.format("[%s] %s: %s%n",
                        record.getLevel().getName(),
                        new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(record.getMillis())),
                        record.getMessage());
            }
        });
        logger.addHandler(handler);
    }

    public static void stepInfo(String message) {
        logger.info(message);
    }

    public static void warn(String message) {
        logger.warning(message);
    }

}
