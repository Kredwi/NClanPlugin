package ru.kredwi.clan.permission;

import cn.nukkit.permission.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClanPermissions {

    PERMISSION_CLAN_HELP(new Permission("clan.help")),
    PERMISSION_CLAN_INFO(new Permission("clan.info")),
    PERMISSION_CLAN_LIST(new Permission("clan.list")),
    PERMISSION_CLAN_MEMBERS(new Permission("clan.members")),

    PERMISSION_CLAN_CHAT(new Permission("clan.chat")),
    PERMISSION_CLAN_HOME(new Permission("clan.home")),
    PERMISSION_CLAN_MARKET(new Permission("clan.market")),
    PERMISSION_CLAN_STATS(new Permission("clan.stats")),

    PERMISSION_CLAN_KICK(new Permission("clan.kick")),
    PERMISSION_CLAN_DEMOTE(new Permission("clan.demote")),
    PERMISSION_CLAN_REMOTE(new Permission("clan.remote")),
    PERMISSION_CLAN_DISBAND(new Permission("clan.disband")),
    PERMISSION_CLAN_SETHOME(new Permission("clan.sethome")),
    PERMISSION_CLAN_DELHOME(new Permission("clan.delhome")),
    PERMISSION_CLAN_INVITE(new Permission("clan.invite")),
    PERMISSION_CLAN_CREATE(new Permission("clan.create")),
    PERMISSION_CLAN_PVP(new Permission("clan.pvp")),

    PERMISSION_CLAN_CHANGE_ROLE(new Permission("clan.role.change")),
    PERMISSION_CLAN_SEE_ROLE(new Permission("clan.role.see")),
    PERMISSION_CLAN_CREATE_ROLE(new Permission("clan.role.create"));

    private final Permission permission;

}