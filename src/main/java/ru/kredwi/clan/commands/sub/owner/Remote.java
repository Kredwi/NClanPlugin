package ru.kredwi.clan.commands.sub.owner;

import cn.nukkit.Player;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.Role;
import ru.kredwi.clan.model.Member;
import ru.kredwi.clan.service.ClanService;

public class Remote extends ChangePlayerRoleAbs {

    public Remote(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void changePlayerRole(@NonNull Player sender, @NonNull Member member) {
        Role memberRole = member.getRole();
        var roles = Role.values();
        if ((memberRole.ordinal() + 1) > roles.length) {
            sender.sendMessage("The member already has maximum role");
            return;
        }
        var newRole = Role.values()[memberRole.ordinal() + 1];
        member.setRole(newRole);
        sender.sendMessage(String.format("Role succussfully upgraded to %s", newRole.name()));
    }
}
