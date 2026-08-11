package ru.kredwi.clan.commands.sub.role;

import cn.nukkit.Player;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.model.Member;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.List;
import java.util.Optional;

public abstract class ChangePlayerRoleAbs extends MemberCommand {

    public ChangePlayerRoleAbs(MessagesService messagesService, ClanService clanService) {
        super(messagesService, clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        if (args.isEmpty()) {
            messagesService.sendMessage(player, "clan.error.provide_player_name");
            return;
        }

        Optional<Member> memberClan = clan.getMembers().values().stream()
                .filter(member -> member.getDisplayName().equalsIgnoreCase(args.get(0)))
                .findFirst();
        if (memberClan.isEmpty()) {
            messagesService.sendMessage(player, "clan.error.player_not_found_name", args.get(0));
            return;
        }

        this.changePlayerRole(player, clan, memberClan.get());
    }

    protected abstract void changePlayerRole(@NonNull Player sender, @NonNull Clan clan, @NonNull Member member);
}
