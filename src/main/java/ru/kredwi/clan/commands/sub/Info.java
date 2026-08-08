package ru.kredwi.clan.commands.sub;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.service.ClanService;

import java.util.List;
import java.util.Optional;

public class Info extends MemberCommand {
    public Info(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        Server server = Server.getInstance();

        // TODO rewrite to GUI

        player.sendMessage(
                "clan name " + clan.getName() +
                        "clan online " + clan.getMembers().keySet().stream()
                        .map(server::getPlayer)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .filter(Player::isOnline)
                        .count() +
                        "clan exp " + clan.getStats().getExp() +
                        "clan balance " + clan.getStats().getBalance()

        );
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.USE_PERMISSION;
    }
}
