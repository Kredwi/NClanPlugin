package ru.kredwi.clan.provider;

import lombok.Getter;
import ru.kredwi.clan.io.MessagesFile;

import java.util.List;
import java.util.Map;

public class MessagesProvider {

    private MessagesFile messagesFile;

    @Getter
    private Map<String, List<String>> messages;


    public MessagesProvider(MessagesFile messagesFile) {
        this.messagesFile = messagesFile;

        var content = messagesFile.read();
        if (content == null || content.isEmpty())
            throw new IllegalStateException("Plugin messages is empty. Required action");

        this.messages = content;
    }

    public void reload() {
        this.messages = this.messagesFile.read();
    }

}
