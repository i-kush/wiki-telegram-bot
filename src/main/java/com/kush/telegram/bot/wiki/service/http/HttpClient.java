package com.kush.telegram.bot.wiki.service.http;

import com.kush.telegram.bot.wiki.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class HttpClient {

    public static Response getResponse(String aUrl) {
        HttpURLConnection lConnection = null;
        try {
            lConnection = (HttpURLConnection) new URL(aUrl).openConnection();
            return new Response(
                    lConnection.getRequestMethod(),
                    lConnection.getResponseMessage(),
                    lConnection.getResponseCode()
            );
        } catch (IOException aE) {
            Log.getLogger().log(Level.SEVERE, "Issues with connection via url: " + aUrl, aE);
            throw new UncheckedIOException(aE);
        } finally {
            if (lConnection != null) {
                lConnection.disconnect();
            }
        }
    }

    public static String getPageContent(String aUrl) {
        try (InputStream lPageInputStream = new URL(aUrl).openConnection().getInputStream()) {
            BufferedReader lReader = new BufferedReader(new InputStreamReader(lPageInputStream, StandardCharsets.UTF_8));
            StringBuilder lStringBuilder = new StringBuilder();
            String lCurrentString;
            while ((lCurrentString = lReader.readLine()) != null) {
                lStringBuilder.append(lCurrentString).append(System.lineSeparator());
            }
            return lStringBuilder.toString();
        } catch (IOException aE) {
            Log.getLogger().log(Level.SEVERE, "Can't get input stream from url: " + aUrl, aE);
            throw new UncheckedIOException(aE);
        }
    }
}
