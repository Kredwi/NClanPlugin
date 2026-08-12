package ru.kredwi.clan.commands.sub.control;

import cn.nukkit.Player;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.List;

public class Disband extends MemberCommand {
    public Disband(MessagesService messagesService, ClanService clanService) {
        super(messagesService, clanService);
    }

    @Override
    public boolean requiredArguments() {
        return false;
    }

    @Override
    public @NonNull String getPermission() {
        return ClanPermissions.PERMISSION_CLAN_DISBAND.getPermission();
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        clanService.remove(clan.getId());
        messagesService.sendMessage(player, "clan.success.disbanded");
        // the command for all users who included disband permission
    }
}
