package ru.kredwi.clan.provider.papi;

import cn.nukkit.Player;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;

public class PluginPAPI implements PAPI {

    private final PlaceholderAPI placeholderAPI = PlaceholderAPI.getInstance();

    @Override
    public String translate(String placeholders) {
        return placeholderAPI.translateString(placeholders);
    }

    @Override
    public String translate(String placeholders, Player visitor) {
        return placeholderAPI.translateString(placeholders, visitor);
    }
}
