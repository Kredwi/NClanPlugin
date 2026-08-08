package ru.kredwi.clan.commands.wrapper;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.api.command.SubCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.service.ClanService;

import java.util.List;

@RequiredArgsConstructor
public abstract class MemberCommand implements SubCommand {
    protected final ClanService clanService;

    protected abstract void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args);

    @Override
    public final void onCommand(@NonNull CommandSender sender, @NonNull List<String> args) {
        if (!sender.isPlayer()) {
            sender.sendMessage("Command only for a player");
            return;
        }

        Player player = (Player) sender;

        var clan = clanService.getClanWithUUID(player.getUniqueId());
        if (clan.isEmpty()) {
            sender.sendMessage("You cannot be has any clan");
            return;
        }


        this.onCommand(clan.get(), (Player) sender, args);
    }
}