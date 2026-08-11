package ru.kredwi.clan.commands.sub.info;

import cn.nukkit.Player;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.gui.InfoForm;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;

import java.util.List;

public class Info extends MemberCommand {

    public Info(MessagesService messagesService, ClanService clanService) {
        super(messagesService, clanService);
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        new InfoForm(messagesService, clanService, clan)
                .showForm(player);
    }

    @Override
    public @NonNull Permission getPermission() {
        return ClanPermissions.PERMISSION_CLAN_INFO.getPermission();
    }

    @Override
    public boolean requiredArguments() {
        return false;
    }
}
