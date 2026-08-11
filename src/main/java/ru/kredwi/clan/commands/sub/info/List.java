package ru.kredwi.clan.commands.sub.info;

import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.api.command.SubCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.stream.Collectors;

@AllArgsConstructor
public class List implements SubCommand {

    private MessagesService messagesService;
    private ClanService clanService;

    @Override
    public void onCommand(@NonNull CommandSender sender, java.util.@NonNull List<String> args) {
        messagesService.sendMessage(sender, "clan.info.clan_list", clanService.getClans().stream()
                .map(Clan::getName)
                .collect(Collectors.joining("\n")));
    }

    @Override
    public @NonNull Permission getPermission() {
        return ClanPermissions.PERMISSION_CLAN_LIST.getPermission();
    }
}
