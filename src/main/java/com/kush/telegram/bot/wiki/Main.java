package com.kush.telegram.bot.wiki;

import com.kush.telegram.bot.wiki.commands.AnswersHolder;
import com.kush.telegram.bot.wiki.commands.CommandsHolder;
import com.kush.telegram.bot.wiki.commands.SitesHolder;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Main {

    private static final String COMMANDS_FILE_NAME = "commands.properties";
    private static final String ANSWERS_FILE_NAME = "answers.properties";
    private static final String SITES_FILE_NAME = "sites.properties";

    public static void main(String[] args) throws TelegramApiRequestException {
        Log.getLogger().info("Starting application...");

        ApiContextInitializer.init();
        TelegramBotsApi lTelegramBotsApi = new TelegramBotsApi();

        AnswersHolder.init(ANSWERS_FILE_NAME);
        CommandsHolder.init(COMMANDS_FILE_NAME);
        SitesHolder.init(SITES_FILE_NAME);

        lTelegramBotsApi.registerBot(new DopeGeekBot(args[0]));
    }
}