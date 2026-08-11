package ru.kredwi.clan.provider.economy;

import java.util.UUID;

public interface EconomyAPI {

    double myMoney(UUID id);
    int reduceMoney(UUID player, double amount); // returned value not used but original EconomyAPI required this
    boolean isEnabled();

}
