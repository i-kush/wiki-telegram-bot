package com.kush.telegram.bot.wiki.commands;

import org.junit.Assert;
import org.junit.Test;

public class CommandsHolderTest {

    private static final String STUB = "stub";
    private static final String FILE_NAME = "answers.properties";

    @Test
    public void shouldBeSingleton1() {
        CommandsHolder.init(FILE_NAME);
        Assert.assertEquals(CommandsHolder.getInstance().hashCode(), CommandsHolder.getInstance().hashCode());
    }

    @Test
    public void shouldBeSingleton2() {
        CommandsHolder.init(FILE_NAME);
        CommandsHolder.init(STUB);
        Assert.assertEquals(CommandsHolder.getInstance().hashCode(), CommandsHolder.getInstance().hashCode());
    }
}