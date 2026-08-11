package ru.kredwi.clan.service;

import cn.nukkit.command.CommandSender;
import lombok.RequiredArgsConstructor;
import ru.kredwi.clan.provider.ConfigProvider;
import ru.kredwi.clan.provider.MessagesProvider;

import java.util.List;
import java.util.Map;

import static java.text.MessageFormat.format;

@RequiredArgsConstructor
public class MessagesService {

    private ConfigProvider configProvider;
    private MessagesProvider messagesFile;
    private Map<String, List<String>> messages;

    public void reload() {
        messagesFile.reload();
        this.messages = messagesFile.getMessages();
    }

    public void sendMessage(CommandSender sender, String key, Object...args) {
        List<String> messages1 = messages.get(key);
        if (messages1 == null || messages1.isEmpty()) {
            sender.sendMessage(key);
            return;
        }

        StringBuilder message = new StringBuilder();
        messages1.forEach(msg -> message.append(format(configProvider.getMessagePrefix() + msg, args)));

        sender.sendMessage(message.toString());
    }

}
