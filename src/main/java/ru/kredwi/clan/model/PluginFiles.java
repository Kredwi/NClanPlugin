package ru.kredwi.clan.model;

import ru.kredwi.clan.io.ClanShopFile;
import ru.kredwi.clan.io.FileClan;
import ru.kredwi.clan.io.LevelFile;
import ru.kredwi.clan.io.db.FileClansDB;

public record PluginFiles(
        FileClan clanFile,
        LevelFile levelFile,
        ClanShopFile clanShopFile,
        FileClansDB fileDb
) {
}
