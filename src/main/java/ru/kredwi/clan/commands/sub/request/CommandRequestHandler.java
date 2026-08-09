package ru.kredwi.clan.commands.sub.request;

import cn.nukkit.Player;
import cn.nukkit.Server;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kredwi.clan.Role;
import ru.kredwi.clan.model.Member;
import ru.kredwi.clan.model.MemberStats;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.RequestService;

import java.util.Optional;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CommandRequestHandler {

    private final Server server = Server.getInstance();

    @Getter
    private final ClanService clanService;
    @Getter
    private final RequestService requestService;

    public void onAccept(UUID requested) {
        var reqData = requestService.getRequested(requested);
        if (reqData.isEmpty())
            return;

        var clanForRequestor = clanService.getClanWithUUID(reqData.get().requestor());
        if (clanForRequestor.isEmpty())
            return;


        var requestedPlayerInstance = server.getPlayer(reqData.get().requested());
        var requestorPlayerInstance = server.getPlayer(reqData.get().requestor());

        if (requestedPlayerInstance.isEmpty() || requestorPlayerInstance.isEmpty()) {
            requestedPlayerInstance.get().sendMessage("Request author is leave from game");
            return;
        }

        Member member = new Member(
                requestedPlayerInstance.get().getName(),
                requestedPlayerInstance.get().getUniqueId(),
                Role.DEFAULT,
                System.currentTimeMillis(),
                new MemberStats()
        );

        clanForRequestor.get()
                .getMembers()
                .put(requested, member);
        sendAcceptMessages(requestorPlayerInstance.get(), requestedPlayerInstance.get());
    }

    private void sendAcceptMessages(Player requestor, Player requested) {
        requestor.sendMessage("Player with name " + requested.getName() + " successfully accept your request");
        requested.sendMessage("You successfully accept request to clan join");
    }

    public void onDeny(UUID requested) {
        var reqData = requestService.getRequested(requested);
        if (reqData.isEmpty())
            return;
        requestService.remove(requested);

        var requestedPlayerInstance = server.getPlayer(reqData.get().requested());
        var requestorPlayerInstance = server.getPlayer(reqData.get().requestor());

        sendDenyMessage(requestedPlayerInstance, "You successfully deny request");
        sendDenyMessage(requestorPlayerInstance, "Player deny your request");
    }

    private void sendDenyMessage(Optional<Player> player, String message) {
        player.ifPresent(value -> value.sendMessage(message));
    }
}
