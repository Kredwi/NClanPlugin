package ru.kredwi.clan.commands.sub.members;

import cn.nukkit.Player;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.CommonPermissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.List;

public class Leave extends MemberCommand {

    public Leave(MessagesService messagesService, ClanService clanService) {
        super(messagesService, clanService);
    }

    @Override
    public boolean requiredArguments() {
        return false;
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        if (player.getUniqueId().equals(clan.getOwnerId())) {
            messagesService.sendMessage(player, "clan.error.leave.owner");
            return;
        }

        clan.getMembers()
                .remove(player.getUniqueId());
        messagesService.sendMessage(player, "clan.success.leave");
    }

    @Override
    public @NonNull Permission getPermission() {
        return CommonPermissions.CLAN_USE_PERMISSION.getPermission();
    }
}
