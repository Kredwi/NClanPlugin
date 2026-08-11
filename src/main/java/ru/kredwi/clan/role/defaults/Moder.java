package ru.kredwi.clan.role.defaults;

import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.role.Role;

import java.util.List;

public class Moder extends Role {

    public Moder() {
        super("Moder", 1, List.of(
                ClanPermissions.PERMISSION_CLAN_HELP.getPermission(),
                ClanPermissions.PERMISSION_CLAN_INFO.getPermission(),
                ClanPermissions.PERMISSION_CLAN_LIST.getPermission(),
                ClanPermissions.PERMISSION_CLAN_MEMBERS.getPermission(),
                ClanPermissions.PERMISSION_CLAN_CHAT.getPermission(),
                ClanPermissions.PERMISSION_CLAN_HOME.getPermission(),
                ClanPermissions.PERMISSION_CLAN_MARKET.getPermission(),
                ClanPermissions.PERMISSION_CLAN_STATS.getPermission(),
                ClanPermissions.PERMISSION_CLAN_KICK.getPermission(),
                ClanPermissions.PERMISSION_CLAN_DEMOTE.getPermission(),
                ClanPermissions.PERMISSION_CLAN_REMOTE.getPermission(),
                ClanPermissions.PERMISSION_CLAN_INVITE.getPermission(),
                ClanPermissions.PERMISSION_CLAN_PVP.getPermission()
        ));
    }
}
