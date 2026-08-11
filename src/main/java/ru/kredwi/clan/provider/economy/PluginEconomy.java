package ru.kredwi.clan.provider.economy;

import lombok.experimental.Delegate;

public class PluginEconomy implements EconomyAPI {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Delegate
    private me.onebone.economyapi.EconomyAPI api = me.onebone.economyapi.EconomyAPI.getInstance();
}
