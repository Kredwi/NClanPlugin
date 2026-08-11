package ru.kredwi.clan.commands.sub.owner;

import cn.nukkit.Player;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;

import java.util.List;

public class DelHome extends MemberCommand {
    public DelHome(ClanService clanService) {
        super(clanService);
    }

    @Override
    public @NonNull Permission getPermission() {
        return ClanPermissions.PERMISSION_CLAN_DELHOME.getPermission();
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        clan.getSettings().setClanHome(null);
        player.sendMessage("Clan home successfully deleted");
    }
}
