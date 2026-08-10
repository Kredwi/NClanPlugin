package ru.kredwi.clan.commands.sub.owner;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.OwnerCommand;
import ru.kredwi.clan.gui.RoleManagerForm;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.service.ClanService;

import java.util.List;

public class RoleCMD extends OwnerCommand {
    public RoleCMD(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull CommandSender sender, @NonNull List<String> args) {
        new RoleManagerForm(clan).showForm((Player) sender);
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.PERMISSION_CLAN_DENY.getPermission();
    }
}
