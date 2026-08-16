package ru.kredwi.clan.provider.papi;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import ru.kredwi.clan.NClanPlugin;
import ru.kredwi.clan.provider.papi.extension.ClanStats;
import ru.kredwi.clan.provider.papi.extension.MemberExtensions;
import ru.kredwi.clan.provider.papi.extension.MemberStatsExtension;
import ru.kredwi.clan.provider.papi.extension.NameExtension;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

public class PluginPAPI implements PAPI {

    private final PlaceholderAPI placeholderAPI = PlaceholderAPI.getInstance();

    public PluginPAPI() {
        NClanPlugin.log.debug("Loading PAPI Extensions");
        var clanService = NClanPlugin.getInstance().getClanService();
        var messagesService = NClanPlugin.getInstance().getMessagesService();

        new ClanStats(clanService, messagesService);
        new MemberStatsExtension(clanService);
        new MemberExtensions(clanService);
        new NameExtension(clanService);
        NClanPlugin.log.debug("Extensions successfully loaded");
    }

    @Override
    public String translate(String placeholders) {
        return placeholderAPI.translateString(placeholders);
    }

    @Override
    public String translate(String placeholders, Player visitor) {
        return placeholderAPI.translateString(placeholders, visitor);
    }
}
