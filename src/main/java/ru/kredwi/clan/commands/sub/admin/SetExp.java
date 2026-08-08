package ru.kredwi.clan.commands.sub.admin;

import cn.nukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.AdminCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.service.ClanService;

import java.util.List;

public class SetExp extends AdminCommand {

    public SetExp(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull CommandSender sender, @NonNull List<String> args) {
        if (args.isEmpty()) {
            sender.sendMessage("Please provide integer number of balance");
            return;
        }

        int newExp;
        try {
            newExp = Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            sender.sendMessage("Please provide correct int number");
            return;
        }

        clan.getStats()
                .setExp(newExp);

        sender.sendMessage("Experience successfully changed");
    }

}
