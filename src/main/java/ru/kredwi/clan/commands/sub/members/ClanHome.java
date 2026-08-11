package ru.kredwi.clan.commands.sub.members;

import cn.nukkit.Player;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;

import java.util.List;

public class ClanHome extends MemberCommand {
    public ClanHome(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        clan.getSettings()
                .getClanHome()
                .ifPresentOrElse(location ->
                                player.teleport(new cn.nukkit.level.Location(location.x(), location.y(), location.z(), location.yaw())),
                        () -> player.sendMessage("Clan home is not set"));
        ;
    }

    @Override
    public @NonNull Permission getPermission() {
        return ClanPermissions.PERMISSION_CLAN_HOME.getPermission();
    }
}
