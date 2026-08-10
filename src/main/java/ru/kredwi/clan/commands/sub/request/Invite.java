package ru.kredwi.clan.commands.sub.request;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.OwnerCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.model.RequestData;
import ru.kredwi.clan.permission.Permissions;

import java.util.List;

public class Invite extends OwnerCommand {

    private final CommandRequestHandler commandRequestHandler;

    public Invite(CommandRequestHandler commandRequestHandler) {
        super(commandRequestHandler.getClanService());
        this.commandRequestHandler = commandRequestHandler;
    }

    private boolean isClanHasSpots(Clan clan) {
        int allowedPlayers = commandRequestHandler.getClanService()
                .getLevel(clan.getStats().getExp()).getMembers();
        int clanMembers = clan.getMembers().size();

        return allowedPlayers < (clanMembers + 1);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull CommandSender sender, @NonNull List<String> args) {
        if (isClanHasSpots(clan)) {
            sender.sendMessage("You clan does have spots");
            return;
        }

        if (args.isEmpty()) {
            sender.sendMessage("Please provide player name for request");
            return;
        }

        Player requestedPlayer = Server.getInstance().getPlayer(args.get(0));
        if (requestedPlayer == null) {
            sender.sendMessage("The player is offline");
            return;
        }

        var requestedPlayerClan = clanService.getClanWithUUID(requestedPlayer.getUniqueId());
        if (requestedPlayerClan.isPresent()) {
            sender.sendMessage("The player already has clan");
            return;
        }

        commandRequestHandler.getRequestService().createRequest(new RequestData(
                ((Player) sender).getUniqueId(),
                requestedPlayer.getUniqueId()
        ));
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.PERMISSION_CLAN_INVITE.getPermission();
    }
}
