package ru.kredwi.clan.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import ru.kredwi.clan.adapter.ClanAdapter;
import ru.kredwi.clan.model.Clan;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class FileClan extends CommonIOFile<Collection<Clan>> {

    public static final String CLAN_FILE = "clan.json";
    private final File datafolder;

    @Override
    protected File getFile() {
        return new File(datafolder, CLAN_FILE);
    }

    @Override
    protected @NonNull Collection<Clan> deserialize(@NonNull String json) {
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

    @Override
    protected String serialize(@NonNull Collection<Clan> t) {
        return ClanAdapter.GSON.toJson(t);
    }

    @Override
    protected @Nullable String getDefaultValue() {
        return "[]"; // empty array
    }
}
