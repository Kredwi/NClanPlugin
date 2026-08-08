package ru.kredwi.clan.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import ru.kredwi.clan.NClanPlugin;
import ru.kredwi.clan.adapter.ClanAdapter;
import ru.kredwi.clan.model.Clan;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public record FileClan(File datafolder) {

    public static final String CLAN_FILE = "clan.json";

    public void write(Collection<Clan> clans) {
        if (!datafolder.exists())
            datafolder.mkdirs();

        String json = ClanAdapter.GSON.toJson(clans);
        writeFile(CLAN_FILE, json);
    }

    public Collection<Clan> read() {
        if (!datafolder.exists())
            datafolder.mkdirs();


        String json = readFile(CLAN_FILE);
        JsonArray clansRaw = ClanAdapter.GSON
                .fromJson(json, JsonArray.class);

        if (clansRaw == null || clansRaw.isEmpty())
            return Collections.emptyList();

        ArrayList<Clan> clans = new ArrayList<>();

        for (JsonElement e : clansRaw) {
            clans.add(ClanAdapter.GSON.fromJson(e, Clan.class));
        }
        return clans;
    }

    private String readFile(String fileName) {
        File dbFile = new File(datafolder, fileName);
        var json = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(dbFile)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                json.append(line);
            }
        } catch (IOException e) {
            NClanPlugin.LOG.error("Error of reading clan file", e);
        }
        return json.toString();
    }

    private void writeFile(String fileName, String fileContent) {
        File dbFile = new File(datafolder, fileName);

        dbFile.delete();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dbFile)))) {
            writer.write(fileContent);

        } catch (IOException e) {
            NClanPlugin.LOG.error("Error of writing clan file", e);
        }
    }

}
