package ru.kredwi.clan.service;

import cn.nukkit.command.CommandSender;
import ru.kredwi.clan.NClanPlugin;
import ru.kredwi.clan.provider.ConfigProvider;
import ru.kredwi.clan.provider.MessagesProvider;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

public class MessagesService {

    private final ConfigProvider configProvider;
    private final MessagesProvider messagesProvider;
    private Map<String, List<String>> messages;

    public MessagesService(ConfigProvider configProvider, MessagesProvider messagesFile) {
        this.configProvider = configProvider;
        this.messagesProvider = messagesFile;

        this.messages = messagesFile.getMessages();
    }

    public void reload() {
        messagesProvider.reload();
        this.messages = messagesProvider.getMessages();
    }

    public String getPrefix() {
        return configProvider.getMessagePrefix();
    }

    public String getMessage(String key, Object... args) {
        return Optional.ofNullable(messages.get(key))
                .map(s -> s.stream()
                        .map(message -> MessageFormat.format(message, args))
                        .collect(Collectors.joining("\n")))
                .orElse(key);
    }

    public void sendMessage(CommandSender sender, String key, Object... args) {
        List<String> messages1 = messages.get(key);
        if (messages1 == null || messages1.isEmpty()) {
            NClanPlugin.log.warning("Message key \"" + key + "\" not found");
            sender.sendMessage(key);
            return;
        }

        messages1.forEach(msg -> sender.sendMessage(format(configProvider.getMessagePrefix() + msg, args)));
    }

    public String translateBoolean(String ctx, boolean bool) {
        String key = ctx + "." + bool;

        return String.join("\n",
                Optional.ofNullable(messages.get(key))
                        .orElseGet(() -> List.of(String.valueOf(bool))));
    }

}
