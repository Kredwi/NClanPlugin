package ru.kredwi.clan;

import cn.nukkit.command.PluginCommand;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Logger;
import ru.kredwi.clan.commands.MainCommand;
import ru.kredwi.clan.io.FileClan;
import ru.kredwi.clan.io.db.FileClansDB;
import ru.kredwi.clan.listener.PlayerDamageEvent;
import ru.kredwi.clan.listener.PlayerKillPlayer;
import ru.kredwi.clan.listener.PlayerQuitEvent;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.RequestService;

public class NClanPlugin extends PluginBase {

    public static Logger log;

    private FileClan file;
    private FileClansDB db;
    private ClanService clanService;
    private RequestService requestService;

    @Override
    public void onLoad() {
        log = getLogger(); // override to plugin logger
        this.file = new FileClan(getDataFolder());
        this.db = new FileClansDB();
        this.clanService = new ClanService(db);
        this.requestService = new RequestService(clanService);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        var clans = this.file.read();
        if (clans.isEmpty())
            log.debug("Clans is empty");
        this.db.enable(clans);

        MainCommand command = new MainCommand(clanService, requestService);
        ((PluginCommand<?>) getCommand("clan")).setExecutor(command);

        getServer().getPluginManager()
                .registerEvents(new PlayerDamageEvent(clanService), this);
        getServer().getPluginManager()
                .registerEvents(new PlayerQuitEvent(requestService), this);
        getServer().getPluginManager()
                .registerEvents(new PlayerKillPlayer(clanService), this);
    }

    @Override
    public void onDisable() {
        this.file.write(db.getClans());
        this.requestService.clear();
        this.db.disable();
    }
}
