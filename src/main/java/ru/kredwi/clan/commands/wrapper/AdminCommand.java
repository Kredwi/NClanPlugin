package ru.kredwi.clan.commands.wrapper;

import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.api.command.SubCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.service.ClanService;

import java.util.List;

@AllArgsConstructor
public abstract class AdminCommand implements SubCommand {
    protected final ClanService clanService;

    protected abstract void onCommand(@NonNull Clan clan, @NonNull CommandSender sender, @NonNull List<String> args);

    @Override
    public final void onCommand(@NonNull CommandSender sender, @NonNull List<String> args) {
        if (args.isEmpty()) {
            sender.sendMessage("Please provide more arguments. /clan help");
            return;
        }

        var clan = clanService.getClanWithName(args.get(0));
        if (clan.isEmpty()) {
            sender.sendMessage("Clan with name " + args.get(0) + " is not found");
            return;
        }

        this.onCommand(clan.get(), sender, args.subList(1, args.size()));
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.ADMIN_PERMISSION;
    }
}
