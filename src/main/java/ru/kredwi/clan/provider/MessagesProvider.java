package ru.kredwi.clan.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kredwi.clan.io.MessagesFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class MessagesProvider {

    private MessagesFile messagesFile;

    @Getter
    private Map<String, List<String>> messages;

    public void reload() {
        this.messages = this.messagesFile.read();
    }

}
