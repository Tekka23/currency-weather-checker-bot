package com.tekka.myfirstbot.service;

import org.springframework.stereotype.Component;

@Component
public class BotMessageParser {
    public MessageItem parseMessage(String msg){
        msg = msg.trim();
        String PREFIX_FOR_COMMAND = "/";
        if(!msg.contains(PREFIX_FOR_COMMAND)) return new MessageItem(msg, "");
        if(msg.contains(PREFIX_FOR_COMMAND) && !msg.contains(" ")) return new MessageItem(msg, "");
        String [] parsedMsg = msg.split(" ");
        return  new MessageItem(parsedMsg[0], parsedMsg[1]);
    }
}
