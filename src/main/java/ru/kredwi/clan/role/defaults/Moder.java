package ru.kredwi.clan.role.defaults;

import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.role.Role;

import java.util.List;

public class Moder extends Role {

    public Moder() {
        super("Moder", 1, List.of(
                Permissions.PERMISSION_CLAN_HELP.getPermission(),
                Permissions.PERMISSION_CLAN_INFO.getPermission(),
                Permissions.PERMISSION_CLAN_LIST.getPermission(),
                Permissions.PERMISSION_CLAN_MEMBERS.getPermission(),
                Permissions.PERMISSION_CLAN_LEAVE.getPermission(),
                Permissions.PERMISSION_CLAN_ACCEPT.getPermission(),
                Permissions.PERMISSION_CLAN_DENY.getPermission(),
                Permissions.PERMISSION_CLAN_CHAT.getPermission(),
                Permissions.PERMISSION_CLAN_HOME.getPermission(),
                Permissions.PERMISSION_CLAN_MARKET.getPermission(),
                Permissions.PERMISSION_CLAN_STATS.getPermission(),
                Permissions.PERMISSION_CLAN_KICK.getPermission(),
                Permissions.PERMISSION_CLAN_DEMOTE.getPermission(),
                Permissions.PERMISSION_CLAN_REMOTE.getPermission(),
                Permissions.PERMISSION_CLAN_INVITE.getPermission()
        ));
    }
}
