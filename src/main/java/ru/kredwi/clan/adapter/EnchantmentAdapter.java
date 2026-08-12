package ru.kredwi.clan.adapter;

import cn.nukkit.item.enchantment.Enchantment;
import com.google.gson.*;
import ru.kredwi.clan.NClanPlugin;

import java.lang.reflect.Type;

public class EnchantmentAdapter implements JsonDeserializer<Enchantment>, JsonSerializer<Enchantment> {

    @Override
    public Enchantment deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String[] enchRawData = jsonElement.getAsString().split(":");
        if (enchRawData.length < 2)
            throw new IllegalArgumentException("The raw data for enchantment is not valid");
        int enchId;
        int enchLevel;
        try {
            enchId = Integer.parseInt(enchRawData[0]);
            enchLevel = Integer.parseInt(enchRawData[1]);
        } catch (NumberFormatException e) {
            NClanPlugin.log.error("Error of parse int from enchantments", e);
            throw new RuntimeException("Error of parse int from enchantments");
        }

        Enchantment enchantment = Enchantment.getEnchantment(enchId);
        enchantment.setLevel(enchLevel);
        return enchantment;
    }

    @Override
    public JsonElement serialize(Enchantment enchantment, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(enchantment.getId() + ":" + enchantment.getLevel());
    }
}
