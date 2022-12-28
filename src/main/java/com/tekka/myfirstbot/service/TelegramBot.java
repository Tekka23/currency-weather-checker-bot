package com.tekka.myfirstbot.service;

import com.tekka.myfirstbot.config.BotConfig;
import com.tekka.myfirstbot.service.currency.CurrencyDataService;
import com.tekka.myfirstbot.service.weather.WeatherDataService;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.tekka.myfirstbot.config.BotCommandsConfig.createCommands;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final WeatherDataService weatherDataService;
    private final CurrencyDataService currencyDataService;
    private final BotConfig config;
    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);
    private static final String HELP_TEXT =
            """
            Hello there !
            
            This bot is created to alleviate some daily issues you meet every day!
            E.g you will be able soon to check currencies and weather
            
            You can execute commands from main menu:
            
            Click or type /start to start working with bot
            Click or type /help to see this message again
            """;
    @Autowired
    public TelegramBot(WeatherDataService weatherDataService, CurrencyDataService currencyDataService, BotConfig config) {
        this.weatherDataService = weatherDataService;
        this.currencyDataService = currencyDataService;
        this.config = config;
        try {
            this.execute(new SetMyCommands(createCommands(), new BotCommandScopeDefault(), null));
        }
        catch (TelegramApiException e){
            logger.warn("Telegram exception trying to load commands:" + e.getMessage());
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        long chatId;
        if (update.hasMessage() && update.getMessage().hasText()) {
            BotMessageParser botMessageParser = new BotMessageParser();
            chatId = update.getMessage().getChatId();
            MessageItem parsedMsg = botMessageParser.parseMessage(update.getMessage().getText());
            switch (parsedMsg.getCommand()) {
                case "/start" -> startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                case "/help" -> sendMessage(chatId, HELP_TEXT);
                case "/currencyrates" -> sendMessage(chatId,currencyDataService.getMessage());
                case "/weather", "weather" -> sendMessage(chatId, weatherDataService.getWeatherMessage(parsedMsg.getText()));
                default -> sendMessage(chatId, "Sorry, command was not recognized");
            }
        }
    }
    private void startCommandReceived(long chatId, String name){
        String answer = EmojiParser.parseToUnicode("Hi " + name + " , nice to meet you" + " :blush:");
        sendMessage(chatId, answer);
    }
    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage(String.valueOf(chatId), textToSend);
        executeTask(message);
    }
        private void executeTask(SendMessage text){
        try{
            execute(text);
        }
        catch (TelegramApiException e){
            logger.warn("Executing task " + e.getMessage());
        }
        }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }
    @Override
    public String getBotToken() {
        return config.getToken();
    }
}
