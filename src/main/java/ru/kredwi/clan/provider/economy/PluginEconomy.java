package ru.kredwi.clan.provider.economy;

import lombok.experimental.Delegate;

public class PluginEconomy implements EconomyAPI {

    @Delegate
    private me.onebone.economyapi.EconomyAPI api = me.onebone.economyapi.EconomyAPI.getInstance();

    @Override
    public boolean isEnabled() {
        return true;
    }
}
