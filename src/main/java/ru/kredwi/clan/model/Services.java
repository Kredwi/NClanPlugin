package ru.kredwi.clan.model;

import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.provider.ConfigProvider;
import ru.kredwi.clan.provider.economy.EconomyAPI;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.LevelService;
import ru.kredwi.clan.service.MessagesService;
import ru.kredwi.clan.service.RequestService;
import ru.kredwi.clan.shop.ClanShop;

public record Services(
        @NonNull ConfigProvider configProvider,
        @NonNull EconomyAPI economyAPI,
        @NonNull MessagesService messagesService,
        @NonNull ClanService clanService,
        @NonNull RequestService requestService,
        @NonNull LevelService levelService,
        @NonNull ClanShop clanShop,
        @NonNull PluginFiles files) {
}
