package ru.kredwi.clan.gui;

import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.service.MessagesService;
import ru.kredwi.clan.shop.ShopItem;

import java.text.MessageFormat;
import java.util.List;

public class MarketForm extends FormWindowSimple {

    @AllArgsConstructor
    @Data
    class Elements {
        private final ElementLabel label;
        private final ElementButton button;
    }

    public MarketForm(MessagesService messagesService, List<ShopItem> items, double balance) {
        super(messagesService.getMessage("clan.form.market.title"),
                messagesService.getMessage("clan.form.market.balance", balance));

        items.stream()
                .map(e -> new Elements(
                        new ElementLabel(messagesService.getMessage("clan.form.market.meta_data",
                                e.getPrise(), e.getCount())),
                        new ElementButton(
                                MessageFormat.format(e.getName(),
                                        e.getPrise(),
                                        e.getCount())
                        )
                ))
                .forEach(this::register);
    }

    private void register(Elements e) {
        addElement(e.label);
        addElement(e.button);
    }

    public @NonNull FormWindow getForm() {
        return this;
    }

    public void showForm(Player player) {
        player.closeFormWindows();
        player.showFormWindow(getForm());
    }
}
