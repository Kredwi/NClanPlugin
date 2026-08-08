package ru.kredwi.clan.commands.sub.owner;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.OwnerCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.utils.Location;

import java.util.List;

public class SetHome extends OwnerCommand {
    public SetHome(ClanService clanService) {
        super(clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull CommandSender sender, @NonNull List<String> args) {
        Player player = (Player) sender;
        cn.nukkit.level.Location ploc = player.getLocation();
        Location location = new Location(ploc.getX(), ploc.getY(), ploc.getZ(), ploc.yaw);
        clan.getSettings().setClanHome(location);
        sender.sendMessage("Clan home successfully set");
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.BASE_PERMISSION;
    }
}
