package ru.kredwi.clan.commands.sub.info;

import cn.nukkit.Player;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.gui.RoleViewerForm;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.role.Role;
import ru.kredwi.clan.service.ClanService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RolesCMD extends MemberCommand {
    public RolesCMD(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        if (args.isEmpty())
            player.sendMessage(String.format("Clan roles: \n" + clan.getRoles().stream()
                    .sorted(Comparator.comparingInt(Role::getPriority))
                    .map(role -> role.getPriority() + ". " + role.getName() + " permissions " + role.getPermissions().size())
                    .collect(Collectors.joining("\n"))));
        else {
            Optional<Role> role = clan.getRoles()
                    .stream()
                    .filter(role1 -> role1.getName().equalsIgnoreCase(args.get(0)))
                    .findFirst();
            if (role.isEmpty()) {
                player.sendMessage("Role with name " + args.get(0) + " is not found");
                return;
            }

            new RoleViewerForm(role.get()).showForm(player);
        }

    }

    @Override
    public @NonNull Permission getPermission() {
        return ClanPermissions.PERMISSION_CLAN_SEE_ROLE.getPermission();
    }
}
