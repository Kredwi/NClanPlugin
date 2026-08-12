package ru.kredwi.clan.commands.wrapper;

import cn.nukkit.command.CommandSender;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.api.command.SubCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.CommonPermissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.List;

@AllArgsConstructor
public abstract class AdminCommand implements SubCommand {
    protected final MessagesService messagesService;
    protected final ClanService clanService;

    protected abstract void onCommand(@NonNull Clan clan, @NonNull CommandSender sender, @NonNull List<String> args);

    @Override
    public final void onCommand(@NonNull CommandSender sender, @NonNull List<String> args) {
        if (args.isEmpty()) {
            messagesService.sendMessage(sender, "clan.error.more_arguments");
            return;
        }

        var clan = clanService.getClanWithName(args.get(0));
        if (clan.isEmpty()) {
            messagesService.sendMessage(sender, "clan.error.clan_not_found", args.get(0));
            return;
        }

        this.onCommand(clan.get(), sender, args.subList(1, args.size()));
    }

    @Override
    public @NonNull String getPermission() {
        return CommonPermissions.CLAN_ADMIN_PERMISSION.getPermission();
    }
}
