package ru.kredwi.clan.permission;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClanPermissions {

    PERMISSION_CLAN_HELP(("clan.help")),
    PERMISSION_CLAN_INFO(("clan.info")),
    PERMISSION_CLAN_LIST(("clan.list")),
    PERMISSION_CLAN_MEMBERS(("clan.members")),

    PERMISSION_CLAN_CHAT(("clan.chat")),
    PERMISSION_CLAN_HOME(("clan.home")),
    PERMISSION_CLAN_MARKET(("clan.market")),
    PERMISSION_CLAN_STATS(("clan.stats")),

    PERMISSION_CLAN_KICK(("clan.kick")),
    PERMISSION_CLAN_DEMOTE(("clan.demote")),
    PERMISSION_CLAN_REMOTE(("clan.remote")),
    PERMISSION_CLAN_DISBAND(("clan.disband")),
    PERMISSION_CLAN_SETHOME(("clan.sethome")),
    PERMISSION_CLAN_DELHOME(("clan.delhome")),
    PERMISSION_CLAN_INVITE(("clan.invite")),
    PERMISSION_CLAN_CREATE(("clan.create")),
    PERMISSION_CLAN_PVP(("clan.pvp")),

    PERMISSION_CLAN_CHANGE_ROLE(("clan.role.change")),
    PERMISSION_CLAN_SEE_ROLE(("clan.role.see")),
    PERMISSION_CLAN_CREATE_ROLE(("clan.role.create"));

    private final String permission;

}