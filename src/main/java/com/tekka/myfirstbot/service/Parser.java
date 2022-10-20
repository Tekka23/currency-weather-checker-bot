package com.tekka.myfirstbot.service;

public class Parser {
    public MessageParser parseMessage(String msg){
        msg = msg.trim();
        String PREFIX_FOR_COMMAND = "/";
        if(!msg.contains(PREFIX_FOR_COMMAND)) return new MessageParser("", msg);
        if(msg.contains(PREFIX_FOR_COMMAND) && !msg.contains(" ")) return new MessageParser(msg, "");
        String [] parsedMsg = msg.split(" ");
        return  new MessageParser(parsedMsg[0], parsedMsg[1]);
    }
}
