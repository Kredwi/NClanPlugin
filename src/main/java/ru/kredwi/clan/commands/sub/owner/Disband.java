package ru.kredwi.clan.commands.sub.owner;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.OwnerCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.service.ClanService;

import java.util.List;

public class Disband extends OwnerCommand {
    public Disband(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull CommandSender sender, @NonNull List<String> args) {
        if (clan.getOwnerId().equals(((Player) sender).getUniqueId())) {
            clanService.remove(clan.getId());
            sender.sendMessage("Clan successfully disbanded");
        }
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.BASE_PERMISSION;
    }
}
