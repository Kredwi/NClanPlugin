package ru.kredwi.clan.service;

import it.unimi.dsi.fastutil.Pair;
import lombok.RequiredArgsConstructor;
import ru.kredwi.clan.Role;
import ru.kredwi.clan.api.db.ClanDB;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.model.ClanStats;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ClanService {

    private final ClanDB clansDB;

    public void create(int id, UUID ownerId, String name) {
        this.clansDB.create(id, ownerId, name);
    }

    public Optional<Clan> get(int id) {
        return this.clansDB.get(id);
    }

    public Optional<ClanStats> getClanStats(int id) {
        return this.clansDB.get(id)
                .map(Clan::getStats);
    }

    public Optional<Set<Pair<UUID, Role>>> getPlayers(int id) {
        return this.clansDB.get(id)
                .map(Clan::getMembers)
                .map(e -> e.entrySet().stream()
                        .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toSet()));
    }

}
