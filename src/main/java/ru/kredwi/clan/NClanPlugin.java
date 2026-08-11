package ru.kredwi.clan;

import cn.nukkit.command.PluginCommand;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Logger;
import lombok.Getter;
import ru.kredwi.clan.commands.MainCommand;
import ru.kredwi.clan.events.DamageEvent;
import ru.kredwi.clan.io.ClanShopFile;
import ru.kredwi.clan.io.FileClan;
import ru.kredwi.clan.io.LevelFile;
import ru.kredwi.clan.io.db.FileClansDB;
import ru.kredwi.clan.listener.FormListener;
import ru.kredwi.clan.listener.PlayerDamageEvent;
import ru.kredwi.clan.listener.PlayerKillPlayer;
import ru.kredwi.clan.listener.PlayerQuitEvent;
import ru.kredwi.clan.provider.ConfigProvider;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.LevelService;
import ru.kredwi.clan.service.RequestService;
import ru.kredwi.clan.shop.ClanShop;

import java.util.List;

@Getter
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
    private ConfigProvider configProvider;

    @Override
    public void onLoad() {
        log = getLogger(); // override to plugin logger
        this.clanFile = new FileClan(getDataFolder());
        this.levelFile = new LevelFile(getDataFolder());
        this.clanShopFile = new ClanShopFile(getDataFolder());
        this.db = new FileClansDB();

        this.levelService = new LevelService(configProvider);
        this.clanService = new ClanService(configProvider, levelService, db);
        this.requestService = new RequestService(configProvider);
        this.clanShop = new ClanShop();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.configProvider = new ConfigProvider(null, getConfig());

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

        MainCommand command = new MainCommand(clanService, requestService, clanShop, configProvider);
        ((PluginCommand<?>) getCommand("clan")).setExecutor(command);
        log.debug("Command successfully registered");

        registerEvents();

    }

    private void registerEvents() {
        List<Listener> events = List.of(
                new PlayerDamageEvent(clanService),
                new PlayerQuitEvent(requestService),
                new PlayerKillPlayer(new DamageEvent(configProvider, clanService)),
                new FormListener(clanService, clanShop)
        );
        events.forEach(e -> getServer().getPluginManager()
                .registerEvents(e, this));
        log.debug("Plugin events successfully registered");
    }

    @Override
    public void onDisable() {
        this.clanFile.write(db.getClans());
        this.requestService.clear();
        this.db.disable();
    }
}
