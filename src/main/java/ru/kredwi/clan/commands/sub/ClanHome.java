package ru.kredwi.clan.commands.sub;

import cn.nukkit.Player;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.utils.Location;

import java.util.List;

public class ClanHome extends MemberCommand {
    public ClanHome(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        Location location = clan.getSettings()
                .getClanHome();
        player.teleport(new cn.nukkit.level.Location(location.x(), location.y(), location.z(), location.yaw()));
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.PERMISSION_CLAN_HOME.getPermission();
    }
}
