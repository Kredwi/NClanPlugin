package ru.kredwi.clan.commands.sub.owner;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;

import java.util.List;

public class Kick extends MemberCommand {

    public Kick(ClanService clanService) {
        super(clanService);
    }

    @Override
    public @NonNull Permission getPermission() {
        return ClanPermissions.PERMISSION_CLAN_KICK.getPermission();
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player sender, @NonNull List<String> args) {
        if (args.isEmpty()) {
            sender.sendMessage("Please provide player name");
            return;
        }

        // idk how to fix this deprecated error from bukkit
        IPlayer playerToKick = Server.getInstance().getOfflinePlayer(args.get(0));
        if (playerToKick == null) {
            sender.sendMessage("Player with " + args.get(0) + " name is not found");
            return;
        }

        if (playerToKick.getUniqueId().equals(clan.getOwnerId())) {
            sender.sendMessage("Owner cannot be kicked from a clan");
            return;
        }

        if (playerToKick.getUniqueId().equals(sender.getUniqueId())) {
            sender.sendMessage("You cannot be kick yourself");
            return;
        }

        clan.getMembers()
                .remove(playerToKick.getUniqueId());
        sender.sendMessage("Player successfully kicked");
    }
}
