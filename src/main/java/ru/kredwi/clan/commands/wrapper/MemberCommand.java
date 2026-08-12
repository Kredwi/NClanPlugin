package ru.kredwi.clan.commands.wrapper;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.api.command.SubCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class MemberCommand implements SubCommand {
    protected final MessagesService messagesService;
    protected final ClanService clanService;

    protected abstract void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args);

    public boolean requiredArguments() {
        return true;
    }

    @Override
    public final void onCommand(@NonNull CommandSender sender, @NonNull List<String> args) {
        if (requiredArguments() && args.isEmpty()) {
            messagesService.sendMessage(sender, "clan.error.more_arguments");
            return;
        }

        Player player = (Player) sender;

        var clan = clanService.getClanWithUUID(player.getUniqueId());
        if (clan.isEmpty()) {
            messagesService.sendMessage(sender, "clan.error.no_clan");
            return;
        }

        var memberOptional = Optional.ofNullable(clan.get().getMembers().get(player.getUniqueId()));
        memberOptional.ifPresent(member -> {
            if (member.getRole().getPermissions().contains(getPermission()))
                this.onCommand(clan.get(), player, args);
            else
                messagesService.sendMessage(sender, "clan.error.no_permission_command");
        });
    }
}