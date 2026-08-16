package ru.kredwi.clan.provider.papi.extension;

import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;

public abstract class Extension {

    public  <T> PlaceholderAPI.Builder<T> getGlobalBuilder(String name, Class<T> type) {
        return PlaceholderAPI.getInstance().builder(name, type)
                .autoUpdate(true)
                .updateInterval(1000)
                .processParameters(true);
    }

    public  <T> PlaceholderAPI.Builder<T> getPlayerBuilder(String name, Class<T> type) {
        return PlaceholderAPI.getInstance().builder(name, type)
                .autoUpdate(true)
                .updateInterval(1000)
                .processParameters(true);
    }

}
