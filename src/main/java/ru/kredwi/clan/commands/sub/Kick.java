package ru.kredwi.clan.commands.sub;

import cn.nukkit.IPlayer;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.OwnerCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.service.ClanService;

import java.util.List;

public class Kick extends OwnerCommand {

    public Kick(ClanService clanService) {
        super(clanService);
    }

    @Override
    public void onCommand(@NonNull Clan clan, @NonNull CommandSender sender, @NonNull List<String> args) {
        if (args.isEmpty()) {
            sender.sendMessage("Please provide player name");
            return;
        }

        // idk how to fix this deprecated error from bukkit
        IPlayer playerToKick = Server.getInstance().getOfflinePlayer(args.get(0));
        if (playerToKick == null) {
            sender.sendMessage("Player with " + args.get(0) + "name is not found");
            return;
        }

        clan.getMembers()
                .remove(playerToKick.getUniqueId());
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.BASE_PERMISSION;
    }
}
