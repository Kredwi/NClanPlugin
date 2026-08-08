package ru.kredwi.clan;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Logger;
import ru.kredwi.clan.io.FileClan;
import ru.kredwi.clan.io.db.FileClansDB;
import ru.kredwi.clan.service.ClanService;

public class NClanPlugin extends PluginBase {

    public static Logger LOG;

    private FileClan file;
    private FileClansDB db;
    private ClanService clanService;

    public NClanPlugin() {
        LOG = getLogger();
    }

    @Override
    public void onLoad() {
        this.file = new FileClan(getDataFolder());
        this.db = new FileClansDB();
        this.clanService = new ClanService(db);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        var clans = this.file.read();
        if (clans.isEmpty())
            LOG.debug("Clans is empty");
        this.db.enable(clans);
    }

    @Override
    public void onDisable() {
        this.file.write(db.getClans());
        this.db.disable();
    }
}
