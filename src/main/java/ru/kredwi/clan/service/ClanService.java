package ru.kredwi.clan.service;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import it.unimi.dsi.fastutil.Pair;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.Role;
import ru.kredwi.clan.api.db.ClanDB;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.model.ClanStats;
import ru.kredwi.clan.model.Member;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ClanService {

    private final Cache<UUID, Clan> clanCache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build();

    private final ClanDB clansDB;

    @NonNull
    public Clan create(UUID ownerId, String name) {
        Clan clan = this.clansDB.create(ownerId, name);

        Member member = new Member();
        member.setId(ownerId);
        member.setDisplayName(Server.getInstance()
                .getPlayer(ownerId)
                .map(Player::getName)
                .orElse(""));
        member.setRole(Role.OWNER);

        clan.getMembers()
                .put(ownerId, member);
        return clan;
    }

    public Collection<Clan> getClans() {
        return Collections.unmodifiableCollection(clansDB.getClans());
    }

    public void remove(int clanId) {
        this.clansDB.remove(clanId);
    }

    public Optional<Clan> get(int id) {
        return this.clansDB.get(id);
    }

    public Optional<ClanStats> getClanStats(int id) {
        return this.clansDB.get(id)
                .map(Clan::getStats);
    }

    public Optional<Clan> getClanWithUUID(UUID uuid) {
        var clan = clansDB.getClans()
                .stream()
                .filter(c -> c.getOwnerId().equals(uuid) || c.getMembers()
                        .containsKey(uuid))
                .findFirst();

        clan.ifPresent(value -> clanCache.put(uuid, value));

        return clan;
    }

    public Optional<Clan> getClanWithName(String name) {
        Server server = Server.getInstance();
        IPlayer player = server.getOfflinePlayer(name);
        if (player == null)
            return Optional.empty();
        return this.getClanWithUUID(player.getUniqueId());
    }

    public Optional<Set<Pair<UUID, Member>>> getMembers(int id) {
        return this.clansDB.get(id)
                .map(Clan::getMembers)
                .map(e -> e.entrySet().stream()
                        .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toSet()));
    }

    public void onPlayerKill(UUID killer, UUID victim) {
        var damagerClan = getClanWithUUID(killer);
        var victimClan = getClanWithUUID(victim);

        if (damagerClan.isPresent()) {
            var stats = damagerClan.get().getStats();
            stats.setExp(stats.getExp() + 1);
        }

        if (victimClan.isPresent()) {
            var stats = victimClan.get().getStats();
            stats.setExp(stats.getExp() - 1);
        }
    }

}
