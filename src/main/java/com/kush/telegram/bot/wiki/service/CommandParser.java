package com.kush.telegram.bot.wiki.service;

import com.kush.telegram.bot.wiki.DopeGeekBot;
import com.kush.telegram.bot.wiki.Log;
import com.kush.telegram.bot.wiki.service.http.HttpClient;
import com.kush.telegram.bot.wiki.service.http.Response;
import com.kush.telegram.bot.wiki.commands.AnswersHolder;
import com.kush.telegram.bot.wiki.commands.CommandsHolder;
import com.kush.telegram.bot.wiki.commands.SitesHolder;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class CommandParser {

    private static final String SPECIAL_SPACE = "%20";

    private final List<String> fCommandParts;
    private String fResponseMessage;

    public CommandParser(String[] aCommandPartsRaw) {
        fCommandParts = Arrays.asList(aCommandPartsRaw).subList(1, aCommandPartsRaw.length);
    }

    public void analyze() {
        try {
            switch (CommandsHolder.Commands.valueOf(fCommandParts.get(0))) {
                case WIKI: {
                    doWikiAction();
                    break;
                }
                case SHUT: {
                    doShutAction();
                    break;
                }
            }
        } catch (IllegalArgumentException aE) {
            fResponseMessage = AnswersHolder.getInstance().getTextOf(AnswersHolder.Answers.INVALID_COMMAND);
            Log.getLogger().log(Level.SEVERE, "Bad command", aE);
        }
    }

    private void doWikiAction() {
        if (fCommandParts.size() < 2) {
            fResponseMessage = AnswersHolder.getInstance().getTextOf(AnswersHolder.Answers.WIKI_EMPTY_COMMAND);
            return;
        }
        String lParameterForWikiUrl = getParameterForWikiUrl();
        String lUrl = SitesHolder.getInstance().getTextOf(SitesHolder.Sites.SITE_WIKI) + lParameterForWikiUrl;
        Response lResponse = HttpClient.getResponse(lUrl);
        if (lResponse.getHttpCode() == 200) {
            fResponseMessage = String.format("%s%n%n%s", AnswersHolder.getInstance().getTextOf(AnswersHolder.Answers.HERE_YOU_GO), lUrl);
        } else {
            fResponseMessage = String.format("%s: %s", AnswersHolder.getInstance().getTextOf(AnswersHolder.Answers.INVALID_URL),
                                             lParameterForWikiUrl.replaceAll(SPECIAL_SPACE, " "));
        }
    }

    private String getParameterForWikiUrl() {
        StringBuilder lStringBuilder = new StringBuilder();
        for (int i = 1; i < fCommandParts.size(); i++) {
            String lRawCurrentParam = fCommandParts.get(i).replaceAll("[^a-zA-Z0-9]", SPECIAL_SPACE);
            lStringBuilder.append(capitalizeString(lRawCurrentParam.toLowerCase()));
            if (i != fCommandParts.size() - 1) {
                lStringBuilder.append(SPECIAL_SPACE);
            }
        }
        return lStringBuilder.toString();
    }

    private String capitalizeString(String aValue) {
        char lFirstChar = aValue.charAt(0);
        return Character.isDigit(lFirstChar) ? aValue : ((char) (aValue.charAt(0) - 32) + aValue.substring(1));
    }

    private void doShutAction() {
        try {
            String lRawCommand = String.format("%s_%s", fCommandParts.get(0), fCommandParts.get(1));
            switch (CommandsHolder.Commands.valueOf(lRawCommand)) {
                case SHUT_UP: {
                    DopeGeekBot.setIsShutUp(true);
                }
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException aE) {
            fResponseMessage = AnswersHolder.getInstance().getTextOf(AnswersHolder.Answers.INVALID_COMMAND);
            Log.getLogger().log(Level.SEVERE, "Bad command", aE);
        }
    }

    public String getMessageForResponse() {
        return fResponseMessage;
    }
}
