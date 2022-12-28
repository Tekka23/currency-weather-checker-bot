package com.tekka.myfirstbot.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageItem {
    private String command;
    private String text;

    @Override
    public String toString() {
        return "MessageParser{" +
                "command='" + command + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageItem that)) return false;
        return command.equals(that.command) && text.equals(that.text);
    }
    @Override
    public int hashCode() {
        return Objects.hash(command, text);
    }
}
