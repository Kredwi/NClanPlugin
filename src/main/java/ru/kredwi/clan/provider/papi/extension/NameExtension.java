package ru.kredwi.clan.provider.papi.extension;

import ru.kredwi.clan.service.ClanService;

public class NameExtension extends Extension {

    public NameExtension(ClanService clanService) {
            getGlobalBuilder("clan_name", String.class)
                .visitorLoader(entry -> {
                    var clan = clanService.getClanWithUUID(entry.getPlayer().getUniqueId());
                    return clan.isPresent() ? clan.get().getName() : "";
                })
                .build();
    }
}
