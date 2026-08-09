package ru.kredwi.clan.commands.sub;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.service.ClanService;

import java.text.MessageFormat;
import java.util.List;

public class Chat extends MemberCommand {
    public Chat(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        String messageTemplate = "§6{0} §8=> §f{1}";

        if (args.isEmpty()) {
            player.sendMessage("You cannot be send empty message");
            return;
        }

        String messageText = String.join(" ", args);
        clan.getMembers()
                .forEach((playerId, __) -> Server.getInstance()
                        .getPlayer(playerId)
                        .ifPresent(pl -> pl.sendMessage(MessageFormat.format(messageTemplate,
                                pl.getName(), messageText))));
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.BASE_PERMISSION;
    }
}
