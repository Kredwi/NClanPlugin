package ru.kredwi.clan.events;

import cn.nukkit.Player;
import cn.nukkit.Server;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kredwi.clan.model.Member;
import ru.kredwi.clan.model.MemberStats;
import ru.kredwi.clan.role.Role;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;
import ru.kredwi.clan.service.RequestService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class RequestEvent {

    private final Server server = Server.getInstance();

    @Getter
    private final ClanService clanService;
    @Getter
    private final RequestService requestService;
    @Getter
    private final MessagesService messagesService;

    public void onAccept(UUID requested) {
        var reqData = requestService.getRequested(requested);
        if (reqData.isEmpty())
            return;

        var clanForRequestor = clanService.getClanWithUUID(reqData.get().requestor());
        if (clanForRequestor.isEmpty())
            return;


        var requestedPlayerInstance = server.getPlayer(reqData.get().requested());
        var requestorPlayerInstance = server.getPlayer(reqData.get().requestor());

        if (requestedPlayerInstance.isEmpty())
            return;

        if (requestorPlayerInstance.isEmpty()) {
            messagesService.sendMessage(requestedPlayerInstance.get(), "clan.request.author_left");
            return;
        }
        Role role;
        if (clanForRequestor.get().getRoles().isEmpty()) {
            role = new ru.kredwi.clan.role.defaults.Member();
            clanForRequestor.get().setRoles(List.of(role));
        } else role = clanForRequestor.get().getRoles().get(0);

        Member member = new Member(
                requestedPlayerInstance.get().getName(),
                requestedPlayerInstance.get().getUniqueId(),
                role,
                System.currentTimeMillis(),
                new MemberStats()
        );
        clanForRequestor.get()
                .getMembers()
                .put(requested, member);
        sendAcceptMessages(requestorPlayerInstance.get(), requestedPlayerInstance.get());
        requestService.remove(reqData.get().requested());
    }

    private void sendAcceptMessages(Player requestor, Player requested) {
        messagesService.sendMessage(requestor, "clan.request.accept_success_sender", requested.getName());
        messagesService.sendMessage(requested, "clan.request.accept_success_target");
    }

    public void onDeny(UUID requested) {
        var reqData = requestService.getRequested(requested);
        if (reqData.isEmpty())
            return;
        requestService.remove(requested);

        var requestedPlayerInstance = server.getPlayer(reqData.get().requested());
        var requestorPlayerInstance = server.getPlayer(reqData.get().requestor());

        sendDenyMessage(requestedPlayerInstance, "clan.request.deny_success");
        sendDenyMessage(requestorPlayerInstance, "clan.request.deny_target");
    }

    private void sendDenyMessage(Optional<Player> player, String key) {
        player.ifPresent(value -> messagesService.sendMessage(value, key));
    }
}
