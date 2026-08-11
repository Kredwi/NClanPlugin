package ru.kredwi.clan.commands.sub.info;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.gui.PlayerStatsForm;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Stats extends MemberCommand {


    public Stats(MessagesService messagesService, ClanService clanService) {
        super(messagesService, clanService);
    }

    @Override
    public @NonNull Permission getPermission() {
        return ClanPermissions.PERMISSION_CLAN_STATS.getPermission();
    }

    @Override
    public boolean requiredArguments() {
        return false;
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        String name = args.isEmpty() ? player.getName()
                : args.get(0);

        Server server = Server.getInstance();
        var clanMembers = Collections.unmodifiableSet(clan.getMembers().keySet());
        Optional<IPlayer> clanMember = clanMembers.stream()
                .map(server::getOfflinePlayer)
                .filter(offlinePlayer -> offlinePlayer.getName().equalsIgnoreCase(name))
                .findFirst();

        if (clanMember.isEmpty()) {
            messagesService.sendMessage(player, "clan.error.player_not_found");
            return;
        }

        new PlayerStatsForm(messagesService, clan.getMembers().get(clanMember.get().getUniqueId()))
                .showForm(player);
    }
}
