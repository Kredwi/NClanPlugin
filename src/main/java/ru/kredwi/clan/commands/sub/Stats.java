package ru.kredwi.clan.commands.sub;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementHeader;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.service.ClanService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Stats extends MemberCommand {

    public Stats(ClanService clanService) {
        super(clanService);
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.BASE_PERMISSION;
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        Server server = Server.getInstance();
        var clanMembers = Collections.unmodifiableSet(clan.getMembers().keySet());
        Optional<IPlayer> clanMember = clanMembers.stream()
                .map(server::getOfflinePlayer)
                .filter(offlinePlayer -> offlinePlayer.getName().equalsIgnoreCase(args.get(0)))
                .findFirst();

        if (clanMember.isEmpty()) {
            player.sendMessage("Player cannot be found");
            return;
        }

        ElementHeader header = new ElementHeader("Information about " + clanMember.get().getName());
        ElementLabel role = new ElementLabel("Role: " + clan.getMembers()
                .get(clanMember.get().getUniqueId()).getRole().name());


        FormWindowCustom fwc = new FormWindowCustom("Information about " + clanMember.get().getName(),
                List.of(header, role));
        player.showFormWindow(fwc);
    }
}
