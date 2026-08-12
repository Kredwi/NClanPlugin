package ru.kredwi.clan.io.db;

import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.api.db.ClanDB;
import ru.kredwi.clan.model.Clan;

import java.util.*;

public class FileClansDB implements ClanDB {

    private final Map<UUID, Clan> clans = new HashMap<>();

    public FileClansDB(@NonNull Collection<Clan> clans) {
        clans.forEach(c -> this.clans.put(c.getId(), c));
    }

    public @NonNull Clan create(@NonNull UUID ownerId, @NonNull String name) {
        UUID id = UUID.randomUUID();
        Clan clan = Clan.of(id, ownerId, name);
        clans.put(id, clan);
        return clan;
    }

    @Override
    public void remove(UUID clanId) {
        this.clans.remove(clanId);
    }

    public @NonNull Optional<Clan> get(UUID id) {
        return Optional.ofNullable(clans.get(id));
    }

    public @NonNull Collection<Clan> getClans() {
        return clans.values();
    }

    public void enable() {
        // for other db api (file db not required)
    }

    public void disable() {
        // for other db api (file db not required)
    }

}
