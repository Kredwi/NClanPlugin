package ru.kredwi.clan.api.db;

import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.model.Clan;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ClanDB {

    void enable(@NonNull Collection<Clan> clans);

    void disable();

    @NonNull
    Clan create(@NonNull UUID ownerId, @NonNull String name);

    void remove(int clanId);

    @NonNull
    Optional<Clan> get(int id);

    @NonNull
    Collection<Clan> getClans();

}
