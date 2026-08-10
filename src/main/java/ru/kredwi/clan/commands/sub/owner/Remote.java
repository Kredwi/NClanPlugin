package ru.kredwi.clan.commands.sub.owner;

import cn.nukkit.Player;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.DefaultRoles;
import ru.kredwi.clan.model.Member;
import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.role.Role;
import ru.kredwi.clan.service.ClanService;

public class Remote extends ChangePlayerRoleAbs {

    public Remote(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void changePlayerRole(@NonNull Player sender, @NonNull Member member) {
        // TODO REWRITE LOGIC
//        Role memberRole = member.getRole();
//        var roles = ;
//        if ((memberRole.ordinal() + 1) > roles.length) {
//            sender.sendMessage("The member already has maximum role");
//            return;
//        }
//        var newRole = DefaultRoles.values()[memberRole.ordinal() + 1];
//        member.setRole(newRole);
//        sender.sendMessage(String.format("Role succussfully upgraded to %s", newRole.name()));
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.PERMISSION_CLAN_REMOTE.getPermission();
    }
}
