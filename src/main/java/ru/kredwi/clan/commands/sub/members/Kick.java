package ru.kredwi.clan.commands.sub.members;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.List;

public class Kick extends MemberCommand {


    public Kick(MessagesService messagesService, ClanService clanService) {
        super(messagesService, clanService);
    }

    @Override
    public @NonNull String getPermission() {
        return ClanPermissions.PERMISSION_CLAN_KICK.getPermission();
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player sender, @NonNull List<String> args) {
        if (args.isEmpty()) {
            messagesService.sendMessage(sender, "clan.error.provide_player_name");
            return;
        }

        // idk how to fix this deprecated error from bukkit
        IPlayer playerToKick = Server.getInstance().getOfflinePlayer(args.get(0));
        if (playerToKick == null) {
            messagesService.sendMessage(sender, "clan.error.player_not_found_name", args.get(0));
            return;
        }

        if (playerToKick.getUniqueId().equals(clan.getOwnerId())) {
            messagesService.sendMessage(sender, "clan.error.cannot_kick_owner");
            return;
        }

        if (playerToKick.getUniqueId().equals(sender.getUniqueId())) {
            messagesService.sendMessage(sender, "clan.error.cannot_kick_self");
            return;
        }

        clan.getMembers()
                .remove(playerToKick.getUniqueId());
        messagesService.sendMessage(sender, "clan.success.player_kicked");
    }
}
