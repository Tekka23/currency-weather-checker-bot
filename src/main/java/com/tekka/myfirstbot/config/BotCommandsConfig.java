package com.tekka.myfirstbot.config;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;
public class BotCommandsConfig {
    public static List<BotCommand> createCommands(){
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
        listOfCommands.add(new BotCommand("/help" , "info how to use this bot"));
        listOfCommands.add(new BotCommand("/settings", "set your preferences"));
        listOfCommands.add(new BotCommand("/currencyrates", "get currency rates on certain data"));
        listOfCommands.add(new BotCommand("/weather", "get current weather in your city"));
        return listOfCommands;
    }
}
