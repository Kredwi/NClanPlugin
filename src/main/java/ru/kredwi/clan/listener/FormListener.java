package ru.kredwi.clan.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.item.Item;
import lombok.RequiredArgsConstructor;
import ru.kredwi.clan.gui.MarketForm;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.shop.ClanShop;
import ru.kredwi.clan.shop.ShopItem;

import java.util.Optional;

@RequiredArgsConstructor
public class FormListener implements Listener {

    private final ClanService clanService;
    private final ClanShop clanShop;

    @EventHandler
    public void onForm(PlayerFormRespondedEvent e) {
        Player player = e.getPlayer();
        FormWindow formWindow = e.getWindow();

        if (!(formWindow instanceof MarketForm marketForm) || formWindow.wasClosed())
            return;

        var clan = clanService.getClanWithUUID(player.getUniqueId());
        if (clan.isEmpty()) {
            player.sendMessage("You does not has clan");
            return;
        }

        int clickedButton = marketForm.getResponse().getClickedButtonId();
        if (clickedButton > clanShop.getItems().size())
            return;
        Optional<ShopItem> shopItem = clanShop.getItem(clickedButton);
        if (shopItem.isEmpty()) {
            player.sendMessage("Item not found");
            return;
        }
        if (shopItem.get().getPrise() > clan.get().getStats().getBalance()) {
            player.sendMessage("You cannot has moneys for the buy");
            return;
        }
        Item buyedItem = new Item(shopItem.get().getItemId());
        buyedItem.setCustomName(shopItem.get().getName());
        buyedItem.setLore(shopItem.get().getLore().toArray(new String[0]));
        buyedItem.setCount(shopItem.get().getCount());
        player.getInventory().addItem(buyedItem);
        clan.get().getStats().setBalance(clan.get().getStats().getBalance() - shopItem.get().getPrise());
        player.sendMessage("You successfully buying item");

    }

}
