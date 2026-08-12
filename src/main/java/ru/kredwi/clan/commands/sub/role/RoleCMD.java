package ru.kredwi.clan.commands.sub.role;

import cn.nukkit.Player;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.gui.RoleManagerForm;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.List;

public class RoleCMD extends MemberCommand {

    public RoleCMD(MessagesService messagesService, ClanService clanService) {
        super(messagesService, clanService);
    }

    @Override
    public boolean requiredArguments() {
        return false;
    }

    @Override
    public @NonNull String getPermission() {
        return ClanPermissions.PERMISSION_CLAN_CHANGE_ROLE.getPermission();
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        new RoleManagerForm(messagesService, clan).showForm(player);
    }
}
