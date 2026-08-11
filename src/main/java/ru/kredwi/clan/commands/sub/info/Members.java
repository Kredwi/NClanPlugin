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
import ru.kredwi.clan.service.MessagesService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
public class Members implements SubCommand {

    private MessagesService messagesService;
    private ClanService clanService;

    @Override
    public void onCommand(@NonNull CommandSender sender, @NonNull List<String> args) {
        if (!sender.isPlayer()) {
            messagesService.sendMessage(sender, "clan.error.only_player");
            return;
        }

        Player player = (Player) sender;

        var clan = clanService.getClanWithUUID(player.getUniqueId());
        if (clan.isEmpty()) {
            messagesService.sendMessage(sender, "clan.error.no_clan");
            return;
        }
        var members = new ArrayList<>(clan.get().getMembers().values())
                .stream()
                .sorted(Comparator.comparingInt(m -> ((Member) m).getMemberStats().getKills()).reversed())
                .map(Member::getDisplayName)
                .toList();

        messagesService.sendMessage(sender, "clan.info.member_list", clan.get().getName(), String.join("\n", members.subList(0, Math.min(members.size(), 10))));
    }

    @Override
    public @NonNull Permission getPermission() {
        return ClanPermissions.PERMISSION_CLAN_MEMBERS.getPermission();
    }
}
