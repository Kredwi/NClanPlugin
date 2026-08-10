package ru.kredwi.clan.commands.sub.owner;

import cn.nukkit.Player;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.model.Member;
import ru.kredwi.clan.service.ClanService;

import java.util.List;
import java.util.Optional;

public abstract class ChangePlayerRoleAbs extends MemberCommand {

    public ChangePlayerRoleAbs(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        if (args.isEmpty()) {
            player.sendMessage("Please provide any player");
            return;
        }

        Optional<Clan> memberClan = clanService.getClanWithName(args.get(0));
        if (memberClan.isEmpty()) {
            player.sendMessage("Player with name " + args.get(0) + " is not found");
            return;
        }

        Optional<Member> member = memberClan.get().getMembers()
                .values().stream()
                .filter(member1 -> member1.getDisplayName().equalsIgnoreCase(args.get(0)))
                .findFirst();

        if (member.isEmpty()) {
            player.sendMessage("Player in the clan not found");
            return;
        }

        this.changePlayerRole(player, member.get());
    }

    protected abstract void changePlayerRole(@NonNull Player sender, @NonNull Member member);
}
