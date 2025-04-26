package com.kush.telegram.bot.wiki.service.http;

public class Response {

    private final String fHttpMethod;
    private final String fHttpBody;
    private final int fHttpCode;

    Response(String aHttpMethod, String aHttpBody, int aHttpCode) {
        fHttpMethod = aHttpMethod;
        fHttpBody = aHttpBody;
        fHttpCode = aHttpCode;
    }

    public String getHttpMethod() {
        return fHttpMethod;
    }

    public String getHttpBody() {
        return fHttpBody;
    }

    public int getHttpCode() {
        return fHttpCode;
    }


    @Override
    public String toString() {
        return "Response{" +
                "fHttpMethod='" + fHttpMethod + '\'' +
                ", fHttpBody='" + fHttpBody + '\'' +
                ", fHttpCode=" + fHttpCode +
                '}';
    }
}
