package ru.kredwi.clan.service;

import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import ru.kredwi.clan.model.Level;

import java.util.TreeMap;

@Getter
public class LevelService {

    public static final int DEFAULT_EXP_COUNT = 10;

    @NonNull
    @Setter
    private TreeMap<Integer, Level> levels = new TreeMap<>();

    public LevelService() {
        // default (override in config file)
        levels.put(DEFAULT_EXP_COUNT, new Level("default", DEFAULT_EXP_COUNT, 10, 5));
    }

    public LevelService(@NonNull TreeMap<Integer, Level> levels) {
        this.levels = levels;
    }

    @Nullable
    public Level getLevel(int clanExp) {
        if (levels.isEmpty())
            return null;

        Integer key = levels.floorKey(clanExp);
        if (key == null)
            key = levels.firstKey();

        return levels.get(key);
    }

}
