package ru.kredwi.clan.io;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.NClanPlugin;
import ru.kredwi.clan.model.Level;
import ru.kredwi.clan.shop.ShopItem;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


// TODO create common class for ClanShopFile and LevelFile
public record ClanShopFile(File clanShopFile) {

    public static final Gson gson = new Gson();
    public static final String CLAN_SHOP_FILE_NAME = "clan_shop.json";

    public ClanShopFile(File clanShopFile) {
        this.clanShopFile = new File(clanShopFile, CLAN_SHOP_FILE_NAME);
    }

    public @NonNull ArrayList<ShopItem> read() {
        if (!clanShopFile.exists()) {
            clanShopFile.getParentFile().mkdirs();
            if (!writeFileFromJar(clanShopFile))
                writeDefaultWrite(clanShopFile);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(clanShopFile)))) {

            StringBuilder json = new StringBuilder();
            String buf;
            while ((buf = reader.readLine()) != null) {
                json.append(buf);
            }

            if (json.isEmpty()) {
                NClanPlugin.log.warning("level file is empty");
                return new ArrayList<>(0);
            }

            var levels = new ArrayList<ShopItem>();

            JsonArray object = gson.fromJson(json.toString(), JsonArray.class);
            object.forEach(e -> {
                ShopItem shopFile = gson.fromJson(e, ShopItem.class);
                levels.add(shopFile);
            });

            return levels;

        } catch (FileNotFoundException e) {
            NClanPlugin.log.error("levelFile is not found", e);
            return new ArrayList<>(0);
        } catch (IOException e) {
            NClanPlugin.log.error("Error of reading level file", e);
            return new ArrayList<>(0);
        }
    }

    private void writeDefaultWrite(File file) {
        String fileContent = gson.toJson(List.of(new Level("default", 0, 0)));

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {

            writer.write(fileContent);

        } catch (FileNotFoundException e) {
            NClanPlugin.log.error("levelFile is not found", e);
        } catch (IOException e) {
            NClanPlugin.log.error("Error of creating default level file", e);
        }
    }

    private boolean writeFileFromJar(File file) {
        try (InputStream in = LevelFile.class.getResourceAsStream("/" + CLAN_SHOP_FILE_NAME)) {
            if (in == null) {
                NClanPlugin.log.warning("File with name " + CLAN_SHOP_FILE_NAME + " cannot be found in jar file");
                return false;
            }

            Files.copy(in, file.toPath());
            return true;
        } catch (IOException e) {
            NClanPlugin.log.error("Error of copy file from jar to disk", e);
            return false;

        }

    }

}
