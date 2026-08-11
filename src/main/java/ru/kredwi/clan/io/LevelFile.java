package ru.kredwi.clan.io;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import ru.kredwi.clan.model.Level;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class LevelFile extends CommonIOFile<Map<Integer, Level>> {


    public static final Gson gson = new Gson();
    public static final String LEVEL_FILE_NAME = "level.json";

    private final File dataFolder;

    @Override
    protected File getFile() {
        return new File(dataFolder, LEVEL_FILE_NAME);
    }

    @Override
    protected @NonNull Map<Integer, Level> deserilize(@NonNull String json) {
        var levels = new HashMap<Integer, Level>();

        JsonArray object = gson.fromJson(json.toString(), JsonArray.class);
        object.forEach(e -> {
            Level level = gson.fromJson(e, Level.class);
            levels.put(level.getExp(), level);
        });

        return levels;
    }

    @Override
    protected String seserilize(@NonNull Map<Integer, Level> t) {
        return "";
    }

    @Override
    protected @Nullable String getDefaultValue() {
        return gson.toJson(List.of(new Level("default", 0, 5, 5)));

    }
}
