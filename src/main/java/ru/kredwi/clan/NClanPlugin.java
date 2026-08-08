package ru.kredwi.clan;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Logger;
import cn.nukkit.utils.MainLogger;
import ru.kredwi.clan.commands.MainCommand;
import ru.kredwi.clan.io.FileClan;
import ru.kredwi.clan.io.db.FileClansDB;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.RequestService;

public class NClanPlugin extends PluginBase {

    public static Logger log = MainLogger.getLogger();

    private FileClan file;
    private FileClansDB db;
    private ClanService clanService;
    private RequestService requestService;

    public NClanPlugin() {
        log = getLogger();
    } // override to plugin logger

    @Override
    public void onLoad() {
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
        getServer().getCommandMap().registerSimpleCommands(command);
    }

    @Override
    public void onDisable() {
        this.file.write(db.getClans());
        this.db.disable();
    }
}
