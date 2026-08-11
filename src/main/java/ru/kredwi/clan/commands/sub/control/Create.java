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

import java.util.List;

@RequiredArgsConstructor
public class Create implements SubCommand {

    private final ConfigProvider configProvider;
    private final ClanService clanService;

    @Override
    public void onCommand(@NonNull CommandSender sender, @NonNull List<String> args) {

        if (!sender.isPlayer()) {
            sender.sendMessage("Command only for a player");
            return;
        }

        Player player = (Player) sender;

        var clan = clanService.getClanWithUUID(player.getUniqueId());
        if (clan.isPresent()) {
            sender.sendMessage("You already has clan");
            return;
        }

        if (args.isEmpty()) {
            sender.sendMessage("Clan name cannot be empty. Usage /clan help");
            return;
        }

        String clanName = args.get(0);
        if (!clanName.matches(configProvider.getClanNameAllowedPattern())) {
            sender.sendMessage("Your clan name cannot allowed");
            return;
        }

        if (clanName.length() > configProvider.getClanNameMaxLength()) {
            sender.sendMessage("You clan name over bounds (please delete symbols)");
            return;
        }

        if (clanName.length() < configProvider.getClanNameMinLength()) {
            sender.sendMessage("You clan name over bounds (please add more symbols)");
            return;
        }

        // TODO write prise for create clan

        Clan instanceOfClan = clanService.create(player.getUniqueId(), clanName);
        sender.sendMessage("Clan successfully created with id " + instanceOfClan.getId());
    }

    @Override
    public @NonNull Permission getPermission() {
        return ClanPermissions.PERMISSION_CLAN_CREATE.getPermission();
    }
}
