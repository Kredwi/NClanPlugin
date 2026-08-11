package ru.kredwi.clan.commands.sub.info;

import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.api.command.SubCommand;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.MessagesService;

import java.util.List;

@AllArgsConstructor
public class Help implements SubCommand {

    private MessagesService messagesService;

    @Override
    public void onCommand(@NonNull CommandSender sender, @NonNull List<String> args) {
        messagesService.sendMessage(sender, "clan.command.help.lines");
    }

    @Override
    public @NonNull Permission getPermission() {
        return ClanPermissions.PERMISSION_CLAN_HELP.getPermission();
    }
}
