package com.kush.telegram.bot.wiki.commands;

public class CommandsHolder extends Communicator<CommandsHolder.Commands> {

    private static CommandsHolder fCommands;

    private CommandsHolder(String aFile) {
        super(aFile);
    }

    public static void init(String aFile) {
        if (fCommands == null) {
            fCommands = new CommandsHolder(aFile);
        }
    }

    public static CommandsHolder getInstance() {
        return fCommands;
    }

    public enum Commands implements Convertible {

        HELP("command.help"),
        HI("command.hi"),
        HELLO("command.hello"),
        YO("command.yo"),
        DOPE("command.key.word"),
        WIKI("command.key.wiki"),
        SHUT("command.key.shut"),
        THANKS("command.thanks"),
        SHUT_UP("command.shut.up");

        private final String fCommand;

        Commands(String aCommand) {
            fCommand = aCommand;
        }

        public String getText() {
            return fCommand;
        }
    }
}
