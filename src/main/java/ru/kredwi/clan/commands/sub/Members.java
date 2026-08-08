package ru.kredwi.clan.commands.sub;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.api.command.SubCommand;
import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.service.ClanService;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Members implements SubCommand {

    private ClanService clanService;

    @Override
    public void onCommand(@NonNull CommandSender sender, @NonNull List<String> args) {
        if (!sender.isPlayer()) {
            sender.sendMessage("Command only for a player");
            return;
        }

        Player player = (Player) sender;

        var clan = clanService.getClanWithUUID(player.getUniqueId());
        if (clan.isEmpty()) {
            sender.sendMessage("You cannot be has any clan");
            return;
        }

        Server server = Server.getInstance();
        String message = clan.get().getMembers().keySet()
                .stream()
                .map(server::getOfflinePlayer)
                .map(IPlayer::getName)
                .collect(Collectors.joining("\n"));

        sender.sendMessage("Member of clan " + clan.get().getName() + "\n" + message);
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.USE_PERMISSION;
    }
}
