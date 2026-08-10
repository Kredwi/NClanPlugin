package ru.kredwi.clan.service;

import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import ru.kredwi.clan.model.Level;

import java.util.HashMap;
import java.util.Map;

@Getter
public class LevelService {

    public static final int DEFAULT_EXP_COUNT = 10;

    @NonNull
    @Setter
    private Map<Integer, Level> levels = new HashMap<>();

    public LevelService() {
        // default (override in config file)
        levels.put(DEFAULT_EXP_COUNT, new Level("default", DEFAULT_EXP_COUNT, 10, 5));
    }

    public LevelService(@NonNull Map<Integer, Level> levels) {
        this.levels = levels;
    }


    public void addLevel(Level level) {
        levels.put(level.getExp(), level);
    }

    public void removeLevel(int exp) {
        levels.remove(exp);
    }

    @Nullable
    public Level getLevel(int clanExp) {
        if (levels.isEmpty())
            return null;

        int candidate = Integer.MIN_VALUE;
        for (Integer exp : levels.keySet()) {
            if (exp <= clanExp && exp > candidate)
                candidate = exp;
        }

        if (candidate == Integer.MIN_VALUE)
            candidate = levels.keySet().stream().min(Integer::compareTo).orElse(DEFAULT_EXP_COUNT);

        return levels.get(candidate);
    }

}
