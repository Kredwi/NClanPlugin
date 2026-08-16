package ru.kredwi.clan.provider.papi.extension;

import ru.kredwi.clan.service.ClanService;

public class MemberExtensions extends Extension {

    public MemberExtensions(ClanService clanService) {
        getGlobalBuilder("clan_member_role", String.class)
                .visitorLoader(entry -> {
                    var playerId = entry.getPlayer().getUniqueId();
                    var clan = clanService.getClanWithUUID(playerId);
                    if (clan.isEmpty())
                        return "";
                    var member = clan.get().getMembers().get(playerId);
                    return member == null ? "" : member.getRole().getName();
                })
                .build();
    }


}
