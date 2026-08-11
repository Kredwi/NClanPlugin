package ru.kredwi.clan.provider.economy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
@AllArgsConstructor
public class PluginEconomyProvider {

    @Delegate
    private EconomyAPI economyAPI;
}
