package ru.kredwi.clan.provider.papi.extension;

import com.creeperface.nukkit.placeholderapi.api.PlaceholderParameters;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.Comparator;

public class ClanStats extends Extension{

    public ClanStats(ClanService clanService, MessagesService messagesService) {
        getGlobalBuilder("clan_clan_stats", String.class)
                .visitorLoader(entry -> {
                    var clans = clanService.getClans();
                    if (clans == null || clans.isEmpty())
                        return "";
                    var sortedClans = clans.stream()
                            .sorted(Comparator.comparingInt((Clan e) -> e.getStats().getExp()).reversed())
                            .map(e -> messagesService.getMessage("placeholder.clan.stats.line",
                                    e.getName(), e.getStats().getExp(),
                                    e.getStats().getBalance(), e.getMembers().size(),
                                    e.getRoles().size()))
                            .toList();

                    PlaceholderParameters.Parameter parameter = entry.getParameters().get("index");

                    if (parameter == null)
                        return sortedClans.get(0);

                    int number;
                    try {
                        number = Integer.parseInt(parameter.getValue());
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Parameter for placeholder with name " + parameter.getName() + " is not number value");
                    }

                    if (number <= 0 || sortedClans.size() < number)
                        return "";

                    return sortedClans.get(number);
                })
                .build();
    }

}
