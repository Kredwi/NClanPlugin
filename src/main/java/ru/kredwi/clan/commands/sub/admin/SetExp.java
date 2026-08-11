package ru.kredwi.clan.commands.sub.admin;

import cn.nukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.AdminCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.List;

public class SetExp extends AdminCommand {


    public SetExp(MessagesService messagesService, ClanService clanService) {
        super(messagesService, clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull CommandSender sender, @NonNull List<String> args) {
        if (args.isEmpty()) {
            messagesService.sendMessage(sender, "clan.error.provide_integer");
            return;
        }

        int newExp;
        try {
            newExp = Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            messagesService.sendMessage(sender, "clan.error.invalid_integer");
            return;
        }

        int initExp = clan.getStats().getExp();
        clan.getStats().setExp(newExp);
        int finExp = clan.getStats().getExp();
        messagesService.sendMessage(sender, "clan.success.exp_changed");

        clanService.onChangeExp(clan, initExp, finExp);
    }
}
