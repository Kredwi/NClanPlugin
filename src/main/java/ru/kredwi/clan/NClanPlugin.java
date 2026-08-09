package ru.kredwi.clan;

import cn.nukkit.command.PluginCommand;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Logger;
import ru.kredwi.clan.commands.MainCommand;
import ru.kredwi.clan.io.ClanShopFile;
import ru.kredwi.clan.io.FileClan;
import ru.kredwi.clan.io.LevelFile;
import ru.kredwi.clan.io.db.FileClansDB;
import ru.kredwi.clan.listener.FormListener;
import ru.kredwi.clan.listener.PlayerDamageEvent;
import ru.kredwi.clan.listener.PlayerKillPlayer;
import ru.kredwi.clan.listener.PlayerQuitEvent;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.LevelService;
import ru.kredwi.clan.service.RequestService;
import ru.kredwi.clan.shop.ClanShop;

// TODO add promote and demote command
// TODO reorganize permissions (for any action custom permission)
public class NClanPlugin extends PluginBase {

    public static Logger log;

    private LevelFile levelFile;
    private FileClan clanFile;
    private ClanShopFile clanShopFile;
    private FileClansDB db;
    private ClanService clanService;
    private LevelService levelService;
    private RequestService requestService;
    private ClanShop clanShop;

    @Override
    public void onLoad() {
        log = getLogger(); // override to plugin logger
        this.clanFile = new FileClan(getDataFolder());
        this.levelFile = new LevelFile(getDataFolder());
        this.clanShopFile = new ClanShopFile(getDataFolder());
        this.db = new FileClansDB();
        this.levelService = new LevelService();
        this.clanService = new ClanService(levelService, db);
        this.requestService = new RequestService(clanService);
        this.clanShop = new ClanShop();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        var levels = this.levelFile.read();
        levelService.setLevels(levels);
        log.debug(levelService.getLevels().size() + " levels successfully loaded");

        var clans = this.clanFile.read();
        if (clans.isEmpty())
            log.debug("Clans not loaded. Clans is empty");
        log.debug(clans.size() + " clans successfully loaded");

        this.db.enable(clans);
        log.debug("File database successfully loaded");

        var shopItems = clanShopFile.read();
        this.clanShop.setItems(shopItems);
        log.debug(shopItems.size() + " shop items successfully loaded");

        MainCommand command = new MainCommand(clanService, requestService, clanShop);
        ((PluginCommand<?>) getCommand("clan")).setExecutor(command);
        log.debug("Command successfully registered");

        getServer().getPluginManager()
                .registerEvents(new PlayerDamageEvent(clanService), this);
        getServer().getPluginManager()
                .registerEvents(new PlayerQuitEvent(requestService), this);
        getServer().getPluginManager()
                .registerEvents(new PlayerKillPlayer(clanService), this);
        getServer().getPluginManager()
                .registerEvents(new FormListener(clanService, clanShop), this);
        log.debug("Plugin events successfully registered");

    }

    @Override
    public void onDisable() {
        this.clanFile.write(db.getClans());
        this.requestService.clear();
        this.db.disable();
    }
}
