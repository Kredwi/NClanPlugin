package ru.kredwi.clan.commands.sub.control;

import cn.nukkit.Player;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.List;

public class DelHome extends MemberCommand {

    public DelHome(MessagesService messagesService, ClanService clanService) {
        super(messagesService, clanService);
    }

    @Override
    public boolean requiredArguments() {
        return false;
    }

    @Override
    public @NonNull Permission getPermission() {
        return ClanPermissions.PERMISSION_CLAN_DELHOME.getPermission();
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        clan.getSettings().setClanHome(null);
        messagesService.sendMessage(player, "clan.success.home_deleted");
    }
}
