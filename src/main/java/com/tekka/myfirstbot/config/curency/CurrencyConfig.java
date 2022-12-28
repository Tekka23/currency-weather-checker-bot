package com.tekka.myfirstbot.config.curency;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class CurrencyConfig {
    @Value("${currency.url}")
    private String CBRUrl;

    public String getCBRUrl() {
        return CBRUrl;
    }
}
