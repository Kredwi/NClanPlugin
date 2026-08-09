package ru.kredwi.clan.gui;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.shop.ShopItem;

import java.util.List;

public class MarketForm extends FormWindowSimple {

    public MarketForm(List<ShopItem> items, double balance) {
        super("Clan shop", "Clan balance " + balance);

        items.stream()
                .map(e -> new ElementButton(e.getName()))
                .forEach(this::addElement);
    }

    public @NonNull FormWindow getForm() {
        return this;
    }

    public void showForm(Player player) {
        player.closeFormWindows();
        player.showFormWindow(getForm());
    }
}
