package ru.kredwi.clan.commands.sub.request;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.api.command.SubCommand;
import ru.kredwi.clan.permission.CommonPermissions;

import java.util.List;

@AllArgsConstructor
public class Deny implements SubCommand {

    private CommandRequestHandler commandRequestHandler;

    @Override
    public void onCommand(@NonNull CommandSender sender, @NonNull List<String> args) {
        if (sender instanceof Player player)
            commandRequestHandler.onDeny(player.getUniqueId());
    }

    @Override
    public @NonNull Permission getPermission() {
        return CommonPermissions.CLAN_USE_PERMISSION.getPermission();

    }
}
