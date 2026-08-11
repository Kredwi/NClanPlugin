package ru.kredwi.clan.provider;

import cn.nukkit.utils.Config;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kredwi.clan.service.MessagesService;

@AllArgsConstructor
public class ConfigProvider {
    @Getter
    private final MessagesService messageProvider;
    private final Config config;

    public void reload() {
        config.reload();
    }

    public int getRequestExpireMinutes() {
        return config.getInt("request-expire-minutes", 5);
    }

    public int getDefaultClanExp() {
        return config.getInt("default-clan-exp", 0);
    }

    public double getDefaultClanBalance() {
        return config.getDouble("default-clan-balance", 0.0);
    }

    public String getShopCurrencySymbol() {
        return config.getString("shop-currency-symbol", "$");
    }

    public String getMessagePrefix() {
        return config.getString("message-prefix", "§6[Clan] §r");
    }

    public double getClanCreatePrice() {
        return config.getDouble("clan.create-price", 50000.0);
    }

    public int getClanNameMinLength() {
        return config.getInt("clan.name-min-length", 3);
    }

    public int getClanNameMaxLength() {
        return config.getInt("clan.name-max-length", 16);
    }

    public String getClanNameAllowedPattern() {
        return config.getString("clan.name-allowed-pattern", "^[a-zA-Z0-9_]+$");
    }

    public boolean isClanPvpDefault() {
        return config.getBoolean("clan.pvp-default", false);
    }

    public int getExpPerKill() {
        return config.getInt("exp.per-kill", 1);
    }

    public int getExpPerDeath() {
        return config.getInt("exp.per-death", 1);
    }
}