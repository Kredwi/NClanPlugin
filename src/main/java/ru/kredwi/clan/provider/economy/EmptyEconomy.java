package ru.kredwi.clan.provider.economy;

import ru.kredwi.clan.NClanPlugin;

import java.util.UUID;

public class EmptyEconomy implements EconomyAPI {

    private static EconomyAPI INSTANCE;

    private EmptyEconomy() {
        NClanPlugin.log.warning("Plugin with name EconomyAPI is not present");
        NClanPlugin.log.warning("Loaded default empty economy. (Clan creating is free)");
    }

    public static EconomyAPI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EmptyEconomy();
        }
        return INSTANCE;
    }

    @Override
    public double myMoney(UUID id) {
        return 0;
    }

    @Override
    public int reduceMoney(UUID player, double amount) {
        return 0;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
