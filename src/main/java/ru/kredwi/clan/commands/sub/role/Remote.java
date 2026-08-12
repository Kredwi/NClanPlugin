package ru.kredwi.clan.commands.sub.role;

import cn.nukkit.Player;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.model.Member;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.role.Role;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.ArrayList;
import java.util.Comparator;

public class Remote extends ChangePlayerRoleAbs {

    public Remote(MessagesService messagesService, ClanService clanService) {
        super(messagesService, clanService);
    }

    @Override
    protected void changePlayerRole(@NonNull Player sender, @NonNull Clan clan, @NonNull Member member) {
        if (member.getId().equals(clan.getOwnerId())) {
            messagesService.sendMessage(sender, "clan.error.cannot_change_owner");
            return;
        }

        Role memberRole = member.getRole();
        var sortedRoles = new ArrayList<>(clan.getRoles()).stream()
                .sorted(Comparator.comparingInt(Role::getPriority))
                .toList();
        int index = sortedRoles.indexOf(memberRole);
        if ((index + 1) > sortedRoles.size()) {
            messagesService.sendMessage(sender, "clan.error.max_role");

            return;
        }

        member.setRole(clan.getRoles().get(index + 1));
        messagesService.sendMessage(sender, "clan.success.remoted", member.getDisplayName());
    }

    @Override
    public @NonNull String getPermission() {
        return ClanPermissions.PERMISSION_CLAN_REMOTE.getPermission();
    }
}
