package com.tekka.myfirstbot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class BotMessageItemTest {
    private BotMessageParser underTest;

    @BeforeEach
    void setUp(){
        this.underTest = new BotMessageParser();
    }

    @ParameterizedTest
    @CsvSource({"/weather saint-petersburg,saint-petersburg", "/currency USD,USD", "/weather Kursk,Kursk", "/weather Moscow,Moscow"})
    void isShouldParseCorrectMessage(String msg, String secondName) {
        String [] str = msg.trim().split(" ");
                assertThat(underTest.parseMessage(msg))
                        .isEqualTo(new MessageItem(str[0], secondName))
                        .usingRecursiveComparison();
    }

    @Test
    void isShouldParseCorrectMessageIfOnlyOneArgument() {
        assertThat(underTest.parseMessage("weather"))
                .isEqualTo(new MessageItem("weather", ""))
                .usingRecursiveComparison();
    }
}