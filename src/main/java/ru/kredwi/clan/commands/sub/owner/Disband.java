package ru.kredwi.clan.commands.sub.owner;

import cn.nukkit.Player;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;

import java.util.List;

public class Disband extends MemberCommand {
    public Disband(ClanService clanService) {
        super(clanService);
    }


    @Override
    public @NonNull Permission getPermission() {
        return ClanPermissions.PERMISSION_CLAN_DISBAND.getPermission();
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        if (clan.getOwnerId().equals((player).getUniqueId())) {
            clanService.remove(clan.getId());
            player.sendMessage("Clan successfully disbanded");
        }
    }
}
