package ru.kredwi.clan.commands.sub.owner;

import cn.nukkit.Player;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.gui.RoleCreateForm;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.service.ClanService;

import java.util.List;

public class AddRole extends MemberCommand {
    public AddRole(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        new RoleCreateForm(clan).showForm(player);
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.PERMISSION_CLAN_CREATE_ROLE.getPermission();
    }
}
