package com.kush.telegram.bot.wiki.commands;

import org.junit.Assert;
import org.junit.Test;

public class SitesHolderTest {

    private static final String STUB = "stub";
    private static final String FILE_NAME = "answers.properties";

    @Test
    public void shouldBeSingleton1() {
        SitesHolder.init(FILE_NAME);
        Assert.assertEquals(SitesHolder.getInstance().hashCode(), SitesHolder.getInstance().hashCode());
    }

    @Test
    public void shouldBeSingleton2() {
        SitesHolder.init(FILE_NAME);
        SitesHolder.init(STUB);
        Assert.assertEquals(SitesHolder.getInstance().hashCode(), SitesHolder.getInstance().hashCode());
    }
}