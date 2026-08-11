package ru.kredwi.clan.commands.sub;

import cn.nukkit.Player;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.CommonPermissions;
import ru.kredwi.clan.service.ClanService;

import java.util.List;

public class Leave extends MemberCommand {

    public Leave(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        clan.getMembers()
                .remove(player.getUniqueId());
        player.sendMessage("You successfully leave from clan");
    }

    @Override
    public @NonNull Permission getPermission() {
        return CommonPermissions.CLAN_USE_PERMISSION.getPermission();
    }
}
