package com.kush.telegram.bot.wiki.commands;

import com.kush.telegram.bot.wiki.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;

public abstract class Communicator<T extends Convertible> {

    private final Properties fProperties;

    Communicator(String aFile) {
        fProperties = new Properties();
        try (InputStream lInputStream = getClass().getClassLoader().getResourceAsStream(aFile)) {
            fProperties.load(new InputStreamReader(lInputStream, StandardCharsets.UTF_8));
        } catch (IOException aE) {
            Log.getLogger().log(Level.SEVERE, aE.getMessage(), aE);
        }
    }

    public final Enumeration<?> getTextNames() {
        return fProperties.propertyNames();
    }

    public final String getTextOf(T aKey) {
        return fProperties.getProperty(aKey.getText());
    }

    public final String getTextOf(String aKey) {
        return fProperties.getProperty(aKey);
    }

    @Override
    public String toString() {
        return String.format("%s{%s}", this.getClass().getSimpleName(), this.fProperties);
    }
}
