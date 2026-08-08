package ru.kredwi.clan.commands.sub.owner;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.OwnerCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.service.ClanService;

import java.util.List;

public class Settings extends OwnerCommand {
    public Settings(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull CommandSender sender, @NonNull List<String> args) {
        FormWindowCustom fwc = new FormWindowCustom("Settings of clan " + clan.getName());

        ElementLabel description = new ElementLabel("Clan description");
        ElementToggle enablePvp = new ElementToggle("pvp", clan.getSettings()
                .isPvp());
        fwc.addElement(enablePvp);
        fwc.addElement(description);

        ((Player) sender).showFormWindow(fwc);
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.BASE_PERMISSION;
    }
}
