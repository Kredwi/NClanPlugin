package ru.kredwi.clan.io.db;

import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.api.db.ClanDB;
import ru.kredwi.clan.model.Clan;

import java.util.*;

public class FileClansDB implements ClanDB {

    private final Map<Integer, Clan> clans = new HashMap<>();

    public void create(int id, @NonNull UUID ownerId, @NonNull String name) {
        clans.put(id, Clan.of(id, ownerId, name));
    }

    public Optional<Clan> get(int id) {
        return Optional.of(clans.get(id));
    }

    public Collection<Clan> getClans() {
        return clans.values();
    }

    public void enable(@NonNull Collection<Clan> clans) {
        clans.forEach(c -> this.clans.put(c.getId(), c));
    }

    public void disable() {

    }

}
