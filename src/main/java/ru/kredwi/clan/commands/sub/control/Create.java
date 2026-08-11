package ru.kredwi.clan.commands.sub.control;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.api.command.SubCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.provider.ConfigProvider;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.List;

@RequiredArgsConstructor
public class Create implements SubCommand {

    private final MessagesService messagesService;
    private final ConfigProvider configProvider;
    private final ClanService clanService;

    @Override
    public void onCommand(@NonNull CommandSender sender, @NonNull List<String> args) {
        var ms = messagesService;
        if (!sender.isPlayer()) {
            ms.sendMessage(sender, "clan.error.only_player");
            return;
        }

        Player player = (Player) sender;

        var clan = clanService.getClanWithUUID(player.getUniqueId());
        if (clan.isPresent()) {
            ms.sendMessage(sender, "clan.error.already_in_clan");
            return;
        }

        if (args.isEmpty()) {
            ms.sendMessage(sender, "clan.error.clan_name_empty");
            return;
        }

        String clanName = args.get(0);
        if (!clanName.matches(configProvider.getClanNameAllowedPattern())) {
            ms.sendMessage(sender, "clan.error.name_not_allowed");

            return;
        }

        if (clanName.length() > configProvider.getClanNameMaxLength()) {
            ms.sendMessage(sender, "clan.error.name_too_long");

            return;
        }

        if (clanName.length() < configProvider.getClanNameMinLength()) {
            ms.sendMessage(sender, "clan.error.name_too_short");

            return;
        }

        // TODO write prise for create clan

        Clan instanceOfClan = clanService.create(player.getUniqueId(), clanName);
        ms.sendMessage(sender, "clan.success.clan_created", instanceOfClan.getName());
    }

    @Override
    public @NonNull Permission getPermission() {
        return ClanPermissions.PERMISSION_CLAN_CREATE.getPermission();
    }
}
