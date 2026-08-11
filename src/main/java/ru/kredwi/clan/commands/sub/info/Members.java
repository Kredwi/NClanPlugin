package ru.kredwi.clan.commands.sub.info;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.api.command.SubCommand;
import ru.kredwi.clan.model.Member;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
public class Members implements SubCommand {

    private ClanService clanService;

    @Override
    public void onCommand(@NonNull CommandSender sender, @NonNull List<String> args) {
        if (!sender.isPlayer()) {
            sender.sendMessage("Command only for a player");
            return;
        }

        Player player = (Player) sender;

        var clan = clanService.getClanWithUUID(player.getUniqueId());
        if (clan.isEmpty()) {
            sender.sendMessage("You cannot be has any clan");
            return;
        }
        var members = new ArrayList<>(clan.get().getMembers().values())
                .stream()
                .sorted(Comparator.comparingInt(m -> ((Member) m).getMemberStats().getKills()).reversed())
                .map(Member::getDisplayName)
                .toList();

        sender.sendMessage("Member of clan " + clan.get().getName() + "\n" +
                String.join("\n", members.subList(0, Math.min(members.size(), 10))));
    }

    @Override
    public @NonNull Permission getPermission() {
        return ClanPermissions.PERMISSION_CLAN_MEMBERS.getPermission();
    }
}
