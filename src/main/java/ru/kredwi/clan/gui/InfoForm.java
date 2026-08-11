package ru.kredwi.clan.gui;

import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.model.ClanSettings;
import ru.kredwi.clan.model.ClanStats;
import ru.kredwi.clan.model.Level;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

public class InfoForm extends Form {

    private final FormWindowSimple form;

    public InfoForm(MessagesService messagesService, ClanService clanService, Clan clan) {

        ClanStats clanStats = clan.getStats();
        ClanSettings clanSettings = clan.getSettings();
        Level level = clanService.getLevel(clanStats.getExp());

        this.form = new FormWindowSimple(
                messagesService.getMessage("clan.form.info.title", clan.getName()),
                messagesService.getMessage("clan.form.info.body",
                        clan.getName(), clanSettings.isPvp(),
                        clanStats.getBalance(), clanStats.getExp(),
                        clan.getMembers().size(), level.getMembers()
                ));
    }


    @Override
    public @NonNull FormWindow getForm() {
        return this.form;
    }
}
