package ru.kredwi.clan.api.db;

import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.model.Clan;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ClanDB {

    void enable();

    void disable();

    @NonNull
    Clan create(@NonNull UUID ownerId, @NonNull String name);

    void remove(UUID clanId);

    @NonNull
    Optional<Clan> get(UUID id);

    @NonNull
    Collection<Clan> getClans();

}
