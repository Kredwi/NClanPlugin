package ru.kredwi.clan.commands.sub.admin;

import cn.nukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.AdminCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.List;

public class SetBalance extends AdminCommand {
    public SetBalance(MessagesService service, ClanService clanService) {
        super(service, clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull CommandSender sender, @NonNull List<String> args) {
        if (args.isEmpty()) {
            messagesService.sendMessage(sender, "clan.error.provide_double");
            return;
        }

        double newBalance;
        try {
            newBalance = Double.parseDouble(args.get(0));
        } catch (NumberFormatException e) {
            messagesService.sendMessage(sender, "clan.error.invalid_double");

            return;
        }

        clan.getStats()
                .setBalance(newBalance);

        messagesService.sendMessage(sender, "clan.success.balance_set");
    }
}
