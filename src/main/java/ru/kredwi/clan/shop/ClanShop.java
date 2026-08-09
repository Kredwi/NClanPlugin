package ru.kredwi.clan.shop;

import lombok.Setter;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ClanShop {
    @Setter
    private List<@NonNull ShopItem> items = new ArrayList<>();

    public List<@NonNull ShopItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Optional<ShopItem> getItem(int index) {
        if (index > items.size())
            return Optional.empty();

        return Optional.of(items.get(index));
    }

    public void addItem(ShopItem shopItem) {
        this.items.add(shopItem);
    }

}
