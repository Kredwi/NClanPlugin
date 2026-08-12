package ru.kredwi.clan.provider.papi;

import cn.nukkit.Player;

public class EmptyPAPI implements PAPI {
    public static final PAPI instance = new EmptyPAPI();

    private EmptyPAPI() {
    }

    public static PAPI getInstance() {
        return instance;
    }

    @Override
    public String translate(String placeholders) {
        return "";
    }

    @Override
    public String translate(String placeholders, Player visitor) {
        return "";
    }
}
