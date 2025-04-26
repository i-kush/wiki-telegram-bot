package com.kush.telegram.bot.wiki;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

    private static final String FILE_NAME = DopeGeekBot.class.getSimpleName() + ".log";

    private static Logger logger;

    public static synchronized Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger(Log.class.getName());
            attachLogFileHandler();
        }
        return logger;
    }

    private static void attachLogFileHandler() {
        logger.setUseParentHandlers(false);
        logger.addHandler(new ConsoleHandler());
        try {
            FileHandler logFileHandler = new FileHandler(FILE_NAME);
            logFileHandler.setEncoding(StandardCharsets.UTF_8.toString());
            logFileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(logFileHandler);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot log to file " + FILE_NAME, e);
        }
    }
}