package ru.kredwi.clan;

import lombok.Getter;
import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.role.Role;

import java.util.List;

@Getter
public class DefaultRoles {

    public static final Role OWNER = new Role("Owner", 3, List.of(
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
            Permissions.PERMISSION_CLAN_DISBAND.getPermission(),
            Permissions.PERMISSION_CLAN_SETHOME.getPermission(),
            Permissions.PERMISSION_CLAN_SETTINGS.getPermission(),
            Permissions.PERMISSION_CLAN_REMOVE.getPermission(),
            Permissions.PERMISSION_CLAN_INVITE.getPermission(),
            Permissions.PERMISSION_CLAN_CREATE.getPermission()
    ));
    public static final Role MODER = new Role("Moder", 1, List.of(
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
    public static final Role MEMBER = new Role("Member", 0, List.of(
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
            Permissions.PERMISSION_CLAN_STATS.getPermission()
    ));
    public static final Role DEFAULT = new Role("Default", -1, List.of(
            Permissions.PERMISSION_CLAN_HELP.getPermission(),
            Permissions.PERMISSION_CLAN_LIST.getPermission(),
            Permissions.PERMISSION_CLAN_CREATE.getPermission()
    ));

    private DefaultRoles() {
    }
}