package ru.kredwi.clan.commands.sub;

import cn.nukkit.Player;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.commands.wrapper.MemberCommand;
import ru.kredwi.clan.gui.MarketForm;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.shop.ClanShop;

import java.util.List;

public class Market extends MemberCommand {

    private final ClanShop clanShop;

    public Market(ClanShop clanShop, ClanService clanService) {
        super(clanService);
        this.clanShop = clanShop;
    }

    @Override
    protected void onCommand(@NonNull Clan clan, @NonNull Player player, @NonNull List<String> args) {
        new MarketForm(clanShop.getItems(), clan.getStats().getBalance())
                .showForm(player);
    }

    @Override
    public @NonNull Permission getPermission() {
        return ClanPermissions.PERMISSION_CLAN_MARKET.getPermission();
    }
}
