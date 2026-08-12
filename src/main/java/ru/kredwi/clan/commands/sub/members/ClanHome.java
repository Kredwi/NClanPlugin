package ru.kredwi.clan.commands.sub.members;

import cn.nukkit.Player;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.List;

public class ClanHome extends MemberCommand {

    public ClanHome(MessagesService messagesService, ClanService clanService) {
        super(messagesService, clanService);
    }

    @Override
    public boolean requiredArguments() {
        return false;
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        clan.getSettings()
                .getClanHome()
                .ifPresentOrElse(location ->
                                player.teleport(new cn.nukkit.level.Location(location.x(), location.y(), location.z(), location.yaw())),
                        () -> messagesService.sendMessage(player, "clan.error.home_not_set"));
    }

    @Override
    public @NonNull String getPermission() {
        return ClanPermissions.PERMISSION_CLAN_HOME.getPermission();
    }
}
