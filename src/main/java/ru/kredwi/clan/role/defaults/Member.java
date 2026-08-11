package ru.kredwi.clan.role.defaults;

import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.role.Role;

import java.util.List;

public class Member extends Role {

    public Member() {
        super("Member", 0, List.of(
                ClanPermissions.PERMISSION_CLAN_HELP.getPermission(),
                ClanPermissions.PERMISSION_CLAN_INFO.getPermission(),
                ClanPermissions.PERMISSION_CLAN_LIST.getPermission(),
                ClanPermissions.PERMISSION_CLAN_MEMBERS.getPermission(),
                ClanPermissions.PERMISSION_CLAN_CHAT.getPermission(),
                ClanPermissions.PERMISSION_CLAN_HOME.getPermission(),
                ClanPermissions.PERMISSION_CLAN_MARKET.getPermission(),
                ClanPermissions.PERMISSION_CLAN_STATS.getPermission()
        ));
    }

}
