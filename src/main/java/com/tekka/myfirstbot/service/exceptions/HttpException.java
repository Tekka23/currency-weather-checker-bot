package com.tekka.myfirstbot.service.exceptions;

public class HttpException extends RuntimeException{
    public HttpException(String msg) {
        super(msg);
    }
}
