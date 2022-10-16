package com.example.myfirstbot.service;

import com.example.myfirstbot.config.BotConfig;
import com.example.myfirstbot.model.User;
import com.example.myfirstbot.model.UserRepository;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Component
public class TelegramBot extends TelegramLongPollingBot {
    private static final String YES = "YES_BUTTON";
    private static final String NO = "NO_BUTTON";
    private final UserRepository repository;
    private final BotConfig config;
    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);
    private static final String HELP_TEXT =
            """
            This bot is created to demonstrate Spring capability to handle simple bot app
            
            You can execute commands from main menu
            
            Click or type /start to start working with bot
            
            Click or type /mydata to see data stored about yourself
            
            Click or type /help to see this message again
            """;
    @Autowired
    public TelegramBot(BotConfig config, UserRepository repository) {
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
        listOfCommands.add(new BotCommand("/mydata", "get your data stored"));
        listOfCommands.add(new BotCommand("/deletedata", "delete my data stored"));
        listOfCommands.add(new BotCommand("/help" , "info how to use this bot"));
        listOfCommands.add(new BotCommand("/settings", "set your preferences"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        }
        catch (TelegramApiException e){
            logger.warn("Telegram exp during bot init:" + e.getMessage());
        }
        this.repository = repository;
    }
    @Override
    public void onUpdateReceived(Update update) {
        long chatId;
        if (update.hasMessage() && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            switch (messageText) {
                case "/start" -> {
                    registerUser(update.getMessage());
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                }
                case "/help" -> sendMessage(chatId, HELP_TEXT);
                case "register" -> register(chatId);
                default -> sendMessage(chatId, "Sorry, command was not recognized");
            }
        }
        else if(update.hasCallbackQuery()){
            String callbackData = update.getCallbackQuery().getData();
            chatId = update.getCallbackQuery().getMessage().getChatId();
            int messageId = update.getCallbackQuery().getMessage().getMessageId();
            EditMessageText messageText = new EditMessageText();
            String text;
            if(callbackData.equals(YES)){
                text = "You pressed YES button";
                setEditTextMessage(messageText, text, chatId, messageId);
            }
            else if(callbackData.equals(NO)){
                text = "You pressed NO button";
                setEditTextMessage(messageText, text, chatId, messageId);
            }
           executeTask(messageText);
        }
    }

    private void registerUser(Message msg) {
    if(repository.findById(msg.getChatId()).isEmpty()){
        var chat = msg.getChat();
        User user = new User(msg.getChatId(), chat.getFirstName(), chat.getLastName(), chat.getUserName(), new Timestamp(System.currentTimeMillis()));
        repository.save(user);
        logger.info("user saved:" + user);
    }

    }
    private void startCommandReceived(long chatId, String name){
        String answer = EmojiParser.parseToUnicode("Hi " + name + " , nice to meet you" + " :blush:");
        sendMessage(chatId, answer);
    }
    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage(String.valueOf(chatId), textToSend);
        message.setReplyMarkup(setMarkUp());
        executeTask(message);
    }
    private ReplyKeyboardMarkup setMarkUp(){
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        row.add("weather");
        keyboardRows.add(row);
        row= new KeyboardRow();
        row.add("register");
        row.add("check my data");
        row.add("delete my data");
        keyboardRows.add(row);

        markup.setKeyboard(keyboardRows);
        return markup;
    }
    private void register(long chatId){
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId),"Do you really want to register?");

            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rows = new ArrayList<>();
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            var button = new InlineKeyboardButton();
            button.setText(EmojiParser.parseToUnicode("Yes " + " :white_check_mark:"));
            button.setCallbackData(YES);
            var buttonNo = new InlineKeyboardButton();
            buttonNo.setText(EmojiParser.parseToUnicode("No " + " :x:"));
            buttonNo.setCallbackData(NO);
            buttons.add(button);
            buttons.add(buttonNo);
            rows.add(buttons);
            markup.setKeyboard(rows);
            sendMessage.setReplyMarkup(markup);

            executeTask(sendMessage);
        }
        private void setEditTextMessage(EditMessageText textMessage, String text, long chatId, int messageId){
             textMessage.setMessageId(messageId);
             textMessage.setText(text);
             textMessage.setChatId(chatId);
        }
        private void executeTask(EditMessageText text){
        try {
            execute(text);
        }catch (TelegramApiException e){
            logger.warn("Executing exception: " +  e.getMessage());
        }
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
