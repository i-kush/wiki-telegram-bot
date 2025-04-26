package com.kush.telegram.bot.wiki.commands;

import org.telegram.telegrambots.api.objects.User;

public class AnswersHolder extends Communicator<AnswersHolder.Answers> {

    private static AnswersHolder fAnswers;

    private AnswersHolder(String aFile) {
        super(aFile);
    }

    public static void init(String aFile) {
        if (fAnswers == null) {
            fAnswers = new AnswersHolder(aFile);
        }
    }

    public static AnswersHolder getInstance() {
        return fAnswers;
    }

    public String getUserName(User aUser, boolean aFirstNamePriority) {
        String lUserName = aUser.getUserName();
        if (aFirstNamePriority) {
            if (!isEmpty(aUser.getFirstName())) {
                lUserName = aUser.getFirstName();
            } else if (!isEmpty(aUser.getLastName())) {
                lUserName = aUser.getLastName();
            }
        } else {
            if (!isEmpty(aUser.getLastName())) {
                lUserName = aUser.getLastName();
            } else if (!isEmpty(aUser.getFirstName())) {
                lUserName = aUser.getFirstName();
            }
        }
        return lUserName;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isEmpty(String aValue) {
        return aValue == null || aValue.trim().isEmpty();
    }

    public enum Answers implements Convertible {

        COMMON_HELP("common.help.answer"),
        COMMON_YO("common.yo.answer"),
        COMMON_HI("common.hi"),
        COMMON_HELLO("common.hello"),
        INVALID_COMMAND("common.invalid.command"),
        INVALID_URL("common.invalid.url"),
        HERE_YOU_GO("common.wiki.answer"),
        WIKI_EMPTY_COMMAND("common.wiki.empty.command"),
        COMMON_WRONG_COMMAND("common.no.such.command"),
        COMMON_THANKS("common.thanks");


        private final String fAnswer;

        Answers(String aAnswer) {
            fAnswer = aAnswer;
        }

        public String getText() {
            return fAnswer;
        }
    }
}
