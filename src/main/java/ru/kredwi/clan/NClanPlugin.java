package ru.kredwi.clan;

import cn.nukkit.Server;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Logger;
import lombok.Getter;
import ru.kredwi.clan.commands.MainCommand;
import ru.kredwi.clan.events.DamageEvent;
import ru.kredwi.clan.io.ClanShopFile;
import ru.kredwi.clan.io.FileClan;
import ru.kredwi.clan.io.LevelFile;
import ru.kredwi.clan.io.MessagesFile;
import ru.kredwi.clan.io.db.FileClansDB;
import ru.kredwi.clan.listener.FormListener;
import ru.kredwi.clan.listener.PlayerDamageEvent;
import ru.kredwi.clan.listener.PlayerKillPlayer;
import ru.kredwi.clan.listener.PlayerQuitEvent;
import ru.kredwi.clan.model.PluginFiles;
import ru.kredwi.clan.model.Services;
import ru.kredwi.clan.provider.ConfigProvider;
import ru.kredwi.clan.provider.MessagesProvider;
import ru.kredwi.clan.provider.economy.EconomyAPI;
import ru.kredwi.clan.provider.economy.EmptyEconomy;
import ru.kredwi.clan.provider.economy.PluginEconomy;
import ru.kredwi.clan.provider.economy.PluginEconomyProvider;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.LevelService;
import ru.kredwi.clan.service.MessagesService;
import ru.kredwi.clan.service.RequestService;
import ru.kredwi.clan.shop.ClanShop;

import java.util.List;
import java.util.TreeMap;

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
    private MessagesService messagesService;
    private PluginEconomyProvider pluginEconomyProvider;

    @Override
    public void onLoad() {
        log = getLogger(); // override to plugin logger

        saveDefaultConfig();
        this.configProvider = new ConfigProvider(getConfig());
        MessagesFile mf = new MessagesFile(getDataFolder());
        this.messagesService = new MessagesService(configProvider, new MessagesProvider(mf));


        this.clanFile = new FileClan(getDataFolder());
        this.levelFile = new LevelFile(getDataFolder());
        this.clanShopFile = new ClanShopFile(getDataFolder());

        this.levelService = new LevelService();
        this.requestService = new RequestService(configProvider);
        this.clanShop = new ClanShop();
    }

    @Override
    public void onEnable() {

        Plugin economyAPIPlugin = Server.getInstance().getPluginManager()
                .getPlugin("EconomyAPI");

        EconomyAPI economyAPI = (economyAPIPlugin != null && economyAPIPlugin.isEnabled())
                ? new PluginEconomy()
                : EmptyEconomy.getInstance();
        this.pluginEconomyProvider = new PluginEconomyProvider(economyAPI);
        NClanPlugin.log.debug(economyAPI.getClass().getName() + " loaded");

        var levels = this.levelFile.read();
        levelService.setLevels(new TreeMap<>(levels)); // maybe the not good ideas
        log.debug(levelService.getLevels().size() + " levels successfully loaded");

        this.clanService = loadFileClans();

        var shopItems = clanShopFile.read();
        this.clanShop.setItems(shopItems);
        log.debug(shopItems.size() + " shop items successfully loaded");

        MainCommand command = new MainCommand(
                new Services(configProvider, pluginEconomyProvider,
                        messagesService, clanService,
                        requestService, levelService, clanShop,
                        new PluginFiles(this.clanFile, this.levelFile,
                                this.clanShopFile, this.db))
        );
        ((PluginCommand<?>) getCommand("clan")).setExecutor(command);
        log.debug("Command successfully registered");

        registerEvents();

    }

    private ClanService loadFileClans() {
        var clans = this.clanFile.read();
        if (clans.isEmpty())
            log.debug("Clans not loaded. Clans is empty");

        this.db = new FileClansDB(clans);
        var clanService = new ClanService(messagesService, configProvider, levelService, db);

        log.debug(clans.size() + " clans successfully loaded");

        this.db.enable();
        log.debug("File database successfully loaded");
        return clanService;
    }

    private void registerEvents() {
        List<Listener> events = List.of(
                new PlayerDamageEvent(clanService),
                new PlayerQuitEvent(requestService),
                new PlayerKillPlayer(new DamageEvent(configProvider, clanService)),
                new FormListener(messagesService, clanService, clanShop)
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
