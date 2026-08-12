package ru.kredwi.clan.provider.papi;

import cn.nukkit.Player;

public interface PAPI {

    String translate(String placeholders);

    String translate(String placeholders, Player visitor);

}
