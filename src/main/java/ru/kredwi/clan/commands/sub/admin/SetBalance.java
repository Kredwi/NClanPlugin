package ru.kredwi.clan.commands.sub.admin;

import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.AdminCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.service.ClanService;

import java.util.List;

public class SetBalance extends AdminCommand {
    public SetBalance(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull CommandSender sender, @NonNull List<String> args) {
        if (args.isEmpty()) {
            sender.sendMessage("Please provide double number of balance");
            return;
        }

        double newBalance;
        try {
            newBalance = Double.parseDouble(args.get(0));
        } catch (NumberFormatException e) {
            sender.sendMessage("Please provide correct double number");
            return;
        }

        clan.getStats()
                .setBalance(newBalance);

        sender.sendMessage("Balance successfully set");
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.PERMISSION_CLAN_SETBALANCE.getPermission();
    }
}
