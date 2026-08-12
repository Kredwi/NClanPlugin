package ru.kredwi.clan.commands.sub.control;

import cn.nukkit.Player;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;
import ru.kredwi.clan.utils.Location;

import java.util.List;

public class SetHome extends MemberCommand {
    public SetHome(MessagesService messagesService, ClanService clanService) {
        super(messagesService, clanService);
    }

    @Override
    public boolean requiredArguments() {
        return false;
    }

    @Override
    public @NonNull String getPermission() {
        return ClanPermissions.PERMISSION_CLAN_SETHOME.getPermission();
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        cn.nukkit.level.Location ploc = player.getLocation();
        Location location = new Location(ploc.getX(), ploc.getY(), ploc.getZ(), ploc.yaw);
        clan.getSettings().setClanHome(location);
        messagesService.sendMessage(player, "clan.success.home_set");
    }
}
