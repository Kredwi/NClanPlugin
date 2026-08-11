package ru.kredwi.clan.io;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.shop.ShopItem;

import java.io.File;
import java.util.ArrayList;


@RequiredArgsConstructor
public class ClanShopFile extends CommonIOFile<ArrayList<ShopItem>> {

    public static final Gson gson = new Gson();
    public static final String CLAN_SHOP_FILE_NAME = "clan_shop.json";

    private final File dataFolder;

    @Override
    protected File getFile() {
        return new File(dataFolder, CLAN_SHOP_FILE_NAME);
    }

    @Override
    protected @NonNull ArrayList<ShopItem> deserilize(@NonNull String json) {
        var levels = new ArrayList<ShopItem>();

        JsonArray object = gson.fromJson(json.toString(), JsonArray.class);
        object.forEach(e -> {
            ShopItem shopFile = gson.fromJson(e, ShopItem.class);
            levels.add(shopFile);
        });

        return levels;
    }

    @Override
    protected String seserilize(@NonNull ArrayList<ShopItem> shopItems) {
        return ""; // not need
    }
}
