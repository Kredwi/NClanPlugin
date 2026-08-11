package ru.kredwi.clan.commands.sub.info;

import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.api.command.SubCommand;
import ru.kredwi.clan.permission.CommonPermissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Top implements SubCommand {

    private final MessagesService messagesService;
    private final ClanService clanService;

    @Override
    public void onCommand(@NonNull CommandSender sender, @NonNull List<String> args) {
        var clans = new ArrayList<>(clanService.getClans())
                .subList(0, Math.min(clanService.getClans().size(), 10))
                .stream()
                .sorted(Comparator.comparingInt(e -> e.getStats().getExp()))
                .map(clan ->
                        messagesService.getMessage("clan.command.top.template", clan.getStats().getExp(), clan.getName()))
                .collect(Collectors.joining("\n"));
        // next time fix [Clan] [Clan]
        messagesService.sendMessage(sender, "clan.command.top.lines", clans);
    }

    @Override
    public @NonNull Permission getPermission() {
        return CommonPermissions.CLAN_USE_PERMISSION.getPermission();
    }
}
