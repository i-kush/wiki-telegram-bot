package com.kush.telegram.bot.wiki.service.http;

import org.junit.Assert;
import org.junit.Test;

public class ResponseTest {

    private static final String STUB = "1";
    private static final Response fResponse = new Response(STUB, STUB, Integer.parseInt(STUB));

    @Test
    public void getHttpMethod() {
        Assert.assertEquals(fResponse.getHttpMethod(), STUB);
    }

    @Test
    public void getHttpBody() {
        Assert.assertEquals(fResponse.getHttpBody(), STUB);
    }

    @Test
    public void getHttpCode() {
        Assert.assertEquals(fResponse.getHttpCode(), Integer.parseInt(STUB));
    }
}