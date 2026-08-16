package ru.kredwi.clan.provider.papi.extension;

import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import ru.kredwi.clan.service.ClanService;

public class NameExtension extends Extension {

    public NameExtension(ClanService clanService) {
        PlaceholderAPI.getInstance().builder("clan_name", String.class)
                .autoUpdate(true)
                .updateInterval(1000)
                .processParameters(true)
                .visitorLoader(entry -> {
                    var clan = clanService.getClanWithUUID(entry.getPlayer().getUniqueId());
                    return clan.isPresent() ? clan.get().getName() : "";
                })
                .build();
    }
}
