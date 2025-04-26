package com.kush.telegram.bot.wiki;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.logging.Handler;

public class LogTest {

    @AfterClass
    public static void deleteFile() {
        Handler[] handlers = Log.getLogger().getHandlers();
        if (handlers == null || handlers.length == 0) {
            return;
        }
        handlers[1].close();
        try {
            Files.deleteIfExists(new File(DopeGeekBot.class.getSimpleName() + ".log").toPath());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    public void shouldBeSingleton() {
        Assert.assertEquals(Log.getLogger().hashCode(), Log.getLogger().hashCode());
    }
}
