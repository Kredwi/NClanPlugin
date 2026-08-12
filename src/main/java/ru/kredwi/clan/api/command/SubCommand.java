package ru.kredwi.clan.api.command;

import cn.nukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;

import java.util.List;

public interface SubCommand {

    void onCommand(@NonNull CommandSender sender, @NonNull List<String> args);

    @NonNull
    String getPermission();
}
