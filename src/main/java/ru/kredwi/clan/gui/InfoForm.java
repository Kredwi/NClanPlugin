package ru.kredwi.clan.gui;

import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.model.ClanSettings;
import ru.kredwi.clan.model.ClanStats;
import ru.kredwi.clan.model.Level;
import ru.kredwi.clan.service.ClanService;

public class InfoForm extends Form {

    private final FormWindowSimple form;

    public InfoForm(ClanService clanService, Clan clan) {

        ClanStats clanStats = clan.getStats();
        ClanSettings clanSettings = clan.getSettings();
        Level level = clanService.getLevel(clanStats.getExp());

        this.form = new FormWindowSimple("§6§lInformation of clan: §e§l" + clan.getName(),
                "§8 \n§8 \n§8 \n§8" + "▬".repeat(30) +
                        "\n" +
                        "\n§7▸ §fName: §e§l" + clan.getName() +
                        "\n§7▸ §cPvP mode: §4✘ " + clanSettings.isPvp() +
                        "\n§7▸ §aBalance: §2✧ §a" + clanStats.getBalance() + "$" +
                        "\n§7▸ §bExp: §3✦ §b" + clanStats.getExp() + " EXP" +
                        "\n§7▸ §fMembers: §c✦ §6" + clan.getMembers().size() + "/" + level.getMembers() +
                        "\n" +
                        "\n§8" + "▬".repeat(30));
    }


    @Override
    public @NonNull FormWindow getForm() {
        return this.form;
    }
}
