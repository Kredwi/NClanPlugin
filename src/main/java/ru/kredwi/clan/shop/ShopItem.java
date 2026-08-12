package ru.kredwi.clan.shop;

import cn.nukkit.item.enchantment.Enchantment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopItem {
    private String name;
    private List<String> lore;
    private int itemId; // ItemID
    private int count;
    private int prise;
    private List<Enchantment> enchantments;
}
