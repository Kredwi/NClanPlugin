package ru.kredwi.clan.provider.papi.extension;

import ru.kredwi.clan.service.ClanService;

public class MemberStatsExtension extends Extension {

    public MemberStatsExtension(ClanService clanService) {

        getGlobalBuilder("clan_member_stats_kills", Integer.class)
                .visitorLoader(entry -> {
                    var playerId = entry.getPlayer().getUniqueId();
                    var clan = clanService.getClanWithUUID(playerId);
                    if (clan.isEmpty())
                        return -1;
                    var member = clan.get().getMembers().get(playerId);
                    return member == null ? 0 : member.getMemberStats().getKills();
                })
                .build();

        getGlobalBuilder("clan_member_stats_deaths", Integer.class)
                .visitorLoader(entry -> {
                    var playerId = entry.getPlayer().getUniqueId();
                    var clan = clanService.getClanWithUUID(playerId);
                    if (clan.isEmpty())
                        return -1;
                    var member = clan.get().getMembers().get(playerId);
                    return member.getMemberStats().getDeath();
                })
                .build();
    }
}
