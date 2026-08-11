package ru.kredwi.clan.commands.sub.role;

import cn.nukkit.Player;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.model.Member;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.role.Role;
import ru.kredwi.clan.service.ClanService;

import java.util.ArrayList;
import java.util.Comparator;

public class Demote extends ChangePlayerRoleAbs {

    public Demote(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void changePlayerRole(@NonNull Player sender, @NonNull Clan clan, @NonNull Member member) {
        if (member.getId().equals(clan.getOwnerId())) {
            sender.sendMessage("Role of owner cannot be changed");
            return;
        }

        Role memberRole = member.getRole();
        var sortedRoles = new ArrayList<>(clan.getRoles()).stream()
                .sorted(Comparator.comparingInt(Role::getPriority))
                .toList();
        int index = sortedRoles.indexOf(memberRole);
        if ((index - 1) < 0) {
            sender.sendMessage("The player has minimum role");
            return;
        }

        member.setRole(clan.getRoles().get(index - 1));
        sender.sendMessage(String.format("Role for player %s successfully demoted", member.getDisplayName()));

    }

    @Override
    public @NonNull Permission getPermission() {
        return ClanPermissions.PERMISSION_CLAN_DEMOTE.getPermission();
    }
}
