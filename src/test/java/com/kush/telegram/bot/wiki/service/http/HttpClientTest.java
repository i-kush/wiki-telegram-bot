package com.kush.telegram.bot.wiki.service.http;

import org.junit.Assert;
import org.junit.Test;

public class HttpClientTest {

    private static final String URL = "https://www.google.com.ua/";
    private static final Response RESPONSE = HttpClient.getResponse(URL);
    private static final String CONTENT = HttpClient.getPageContent(URL);

    @Test
    public void code200() {
        Assert.assertEquals(200, RESPONSE.getHttpCode());
    }

    @Test
    public void statusOk() {
        Assert.assertEquals("OK", RESPONSE.getHttpBody().toUpperCase());
    }

    @Test
    public void methodGet() {
        Assert.assertEquals("GET", RESPONSE.getHttpMethod().toUpperCase());
    }

    @Test
    public void notEmpty() {
        Assert.assertFalse(CONTENT.isEmpty());
    }


}