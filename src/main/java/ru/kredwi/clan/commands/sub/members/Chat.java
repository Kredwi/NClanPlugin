package ru.kredwi.clan.commands.sub.members;

import cn.nukkit.Player;
import cn.nukkit.Server;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.NClanPlugin;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.model.Member;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.List;

public class Chat extends MemberCommand {


    public Chat(MessagesService messagesService, ClanService clanService) {
        super(messagesService, clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        if (args.isEmpty()) {
            messagesService.sendMessage(player, "clan.error.empty_message");
            return;
        }

        Member clanMember = clan.getMembers().get(player.getUniqueId());

        NClanPlugin.log.debug(clanMember.getRole().toString());

        String messageText = String.join(" ", args);
        clan.getMembers()
                .forEach((playerId, __) -> Server.getInstance()
                        .getPlayer(playerId)
                        .ifPresent(pl ->
                                messagesService.sendMessage(pl,
                                        "clan.chat.template",
                                        player.getName(), messageText,
                                        clanMember.getRole().getName())));
    }

    @Override
    public @NonNull String getPermission() {
        return ClanPermissions.PERMISSION_CLAN_CHAT.getPermission();
    }
}
