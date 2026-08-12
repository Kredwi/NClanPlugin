package ru.kredwi.clan.commands.sub.members;

import cn.nukkit.Player;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.gui.MarketForm;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;
import ru.kredwi.clan.shop.ClanShop;

import java.util.List;

public class Market extends MemberCommand {

    private final ClanShop clanShop;

    public Market(MessagesService messagesService, ClanService clanService, ClanShop clanShop) {
        super(messagesService, clanService);
        this.clanShop = clanShop;
    }


    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        new MarketForm(messagesService, clanShop.getItems(), clan.getStats().getBalance())
                .showForm(player);
    }

    @Override
    public boolean requiredArguments() {
        return false;
    }

    @Override
    public @NonNull String getPermission() {
        return ClanPermissions.PERMISSION_CLAN_MARKET.getPermission();
    }
}
