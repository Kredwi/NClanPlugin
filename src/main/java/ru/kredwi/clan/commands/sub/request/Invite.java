package ru.kredwi.clan.commands.sub.request;

import cn.nukkit.Player;
import cn.nukkit.Server;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.events.RequestEvent;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.model.RequestData;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.MessagesService;

import java.util.List;

public class Invite extends MemberCommand {

    private final RequestEvent commandRequestHandler;

    public Invite(MessagesService messagesService, RequestEvent commandRequestHandler) {
        super(messagesService, commandRequestHandler.getClanService());
        this.commandRequestHandler = commandRequestHandler;
    }

    private boolean isClanFull(Clan clan) {
        int allowedPlayers = commandRequestHandler.getClanService()
                .getLevel(clan.getStats().getExp()).getMembers();
        int clanMembers = clan.getMembers().size();

        return clanMembers >= allowedPlayers;
    }

    @Override
    public @NonNull String getPermission() {
        return ClanPermissions.PERMISSION_CLAN_INVITE.getPermission();
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player sender, @NonNull List<String> args) {
        if (isClanFull(clan)) {
            messagesService.sendMessage(sender, "clan.error.clan_full");
            return;
        }

        if (args.isEmpty()) {
            messagesService.sendMessage(sender, "clan.error.provide_player_request");
            return;
        }

        Player requestedPlayer = Server.getInstance().getPlayer(args.get(0));
        if (requestedPlayer == null) {
            messagesService.sendMessage(sender, "clan.error.player_offline");
            return;
        }

        var requestedPlayerClan = clanService.getClanWithUUID(requestedPlayer.getUniqueId());
        if (requestedPlayerClan.isPresent()) {
            messagesService.sendMessage(sender, "clan.error.player_already_in_clan");
            return;
        }

        commandRequestHandler.getRequestService().createRequest(new RequestData(
                sender.getUniqueId(),
                requestedPlayer.getUniqueId()
        ));
        // to target
        messagesService.sendMessage(requestedPlayer, "clan.success.you_requested", clan.getName());
        // to requestor
        messagesService.sendMessage(sender, "clan.success.player_requested", requestedPlayer.getName());
    }
}
