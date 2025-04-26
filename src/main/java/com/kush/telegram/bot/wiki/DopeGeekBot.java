package com.kush.telegram.bot.wiki;

import static com.kush.telegram.bot.wiki.commands.AnswersHolder.Answers.COMMON_HELLO;
import static com.kush.telegram.bot.wiki.commands.AnswersHolder.Answers.COMMON_HELP;
import static com.kush.telegram.bot.wiki.commands.AnswersHolder.Answers.COMMON_HI;
import static com.kush.telegram.bot.wiki.commands.AnswersHolder.Answers.COMMON_THANKS;
import static com.kush.telegram.bot.wiki.commands.AnswersHolder.Answers.COMMON_WRONG_COMMAND;
import static com.kush.telegram.bot.wiki.commands.AnswersHolder.Answers.COMMON_YO;
import static com.kush.telegram.bot.wiki.commands.CommandsHolder.Commands.HELP;
import static com.kush.telegram.bot.wiki.commands.CommandsHolder.Commands.HI;

import com.kush.telegram.bot.wiki.commands.AnswersHolder;
import com.kush.telegram.bot.wiki.commands.CommandsHolder;
import com.kush.telegram.bot.wiki.service.CommandParser;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;

public class DopeGeekBot extends TelegramLongPollingBot {

    private static final String SPACE_SEPARATOR = " ";
    private static final String COMMA_AND_SPACE_SEPARATOR = ", ";
    private static boolean fIsShutUp;

    private final ReplyKeyboardMarkup fKeyboardGrid;
    private final String fBotToken;

    private Update fRequest;

    DopeGeekBot(String aBotToken) {
        fKeyboardGrid = new ReplyKeyboardMarkup();
        fKeyboardGrid.setSelective(true);
        fKeyboardGrid.setResizeKeyboard(true);
        fKeyboardGrid.setOneTimeKeyboard(false);

        List<KeyboardRow> lKeyboard = new ArrayList<>();

        KeyboardRow lRow = new KeyboardRow();
        lRow.add(new KeyboardButton(CommandsHolder.getInstance().getTextOf(HI)));
        lRow.add(new KeyboardButton(CommandsHolder.getInstance().getTextOf(HELP)));

        lKeyboard.add(lRow);
        fKeyboardGrid.setKeyboard(lKeyboard);
        fBotToken = aBotToken;
    }

    public static void setIsShutUp(boolean aFIsShutUp) {
        fIsShutUp = aFIsShutUp;
    }

    @Override
    public void onUpdateReceived(Update aUpdate) {
        fRequest = aUpdate;
        Log.getLogger().info("Message received: " + aUpdate);
        sendResponse();
    }

    private synchronized void sendResponse() {
        SendMessage lSendMessage = new SendMessage();
        lSendMessage.enableMarkdown(true);
        lSendMessage.setChatId(fRequest.getMessage().getChatId());
        String lMessageRaw = fRequest.getMessage().getText();
        String lMessage = (lMessageRaw.charAt(0) == '\\' || lMessageRaw.charAt(0) == '/') ? lMessageRaw.substring(1) : lMessageRaw;
        if (!isTextResponseNeeded(lMessage.split(SPACE_SEPARATOR)[0])) {
            Log.getLogger().info("No action for this command...");
            return;
        }
        lSendMessage.setText(getText(lMessage.toUpperCase()));
        lSendMessage.setReplyMarkup(fKeyboardGrid);

        try {
            if (!fIsShutUp) {
                execute(lSendMessage);
            }
        } catch (TelegramApiException aE) {
            Log.getLogger().log(Level.SEVERE, aE.getMessage(), aE);
        }
    }

    private boolean isTextResponseNeeded(String aMessage) {
        Enumeration<?> lMessagesNames = CommandsHolder.getInstance().getTextNames();
        while (lMessagesNames.hasMoreElements()) {
            String lCurrentMessageName = (String) lMessagesNames.nextElement();
            if (CommandsHolder.getInstance().getTextOf(lCurrentMessageName).equalsIgnoreCase(aMessage)) {
                return true;
            }
        }
        return false;
    }

    private String getText(String aCommand) {
        switch (CommandsHolder.Commands.valueOf(aCommand.split(SPACE_SEPARATOR)[0])) {
            case HELP: {
                fIsShutUp = false;
                return AnswersHolder.getInstance().getTextOf(COMMON_HELP);
            }
            case YO: {
                fIsShutUp = false;
                return AnswersHolder.getInstance().getTextOf(COMMON_YO);
            }
            case HI: {
                fIsShutUp = false;
                return getFormattedAnswer(COMMON_HI, COMMA_AND_SPACE_SEPARATOR, true);
            }
            case HELLO: {
                fIsShutUp = false;
                return getFormattedAnswer(COMMON_HELLO, SPACE_SEPARATOR, false);
            }
            case THANKS: {
                return getFormattedAnswer(COMMON_THANKS, COMMA_AND_SPACE_SEPARATOR, true);
            }
            case DOPE: {
                if (!isValid(aCommand)) {
                    return AnswersHolder.getInstance().getTextOf(COMMON_WRONG_COMMAND);
                }
                CommandParser lCommandParser = new CommandParser(aCommand.split(SPACE_SEPARATOR));
                lCommandParser.analyze();
                return lCommandParser.getMessageForResponse();
            }
            default: {
                throw new IllegalArgumentException("There is no such command: " + aCommand);
            }
        }
    }

    private String getFormattedAnswer(AnswersHolder.Answers aAnswer, String aSeparator, boolean aIsFirstNamePriority) {
        String lAnswer = AnswersHolder.getInstance().getTextOf(aAnswer);
        String lUserName = AnswersHolder.getInstance().getUserName(fRequest.getMessage().getFrom(), aIsFirstNamePriority);
        return String.format("%s%s%s.", lAnswer, aSeparator, lUserName);
    }

    private boolean isValid(String aText) {
        return aText.split(SPACE_SEPARATOR).length > 1;
    }

    @Override
    public String getBotUsername() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getBotToken() {
        return fBotToken;
    }
}
