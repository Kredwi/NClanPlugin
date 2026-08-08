package ru.kredwi.clan.commands.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.api.command.SubCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.service.ClanService;

import java.util.stream.Collectors;

@AllArgsConstructor
public class List implements SubCommand {

    private ClanService clanService;

    @Override
    public void onCommand(@NonNull CommandSender sender, java.util.@NonNull List<String> args) {
        sender.sendMessage("All clans in the server \n" + clanService.getClans().stream()
                .map(Clan::getName)
                .collect(Collectors.joining("\n")));
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.USE_PERMISSION;
    }
}
