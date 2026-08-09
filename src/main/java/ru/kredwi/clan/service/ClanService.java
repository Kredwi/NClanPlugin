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
import ru.kredwi.clan.model.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ClanService {

    private final Cache<UUID, Clan> clanCache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build();

    private final LevelService levelService;
    private final ClanDB clansDB;

    public Level getLevel(int exp) {
        return this.levelService.getLevel(exp);
    }

    @NonNull
    public Clan create(UUID ownerId, String name) {
        Clan clan = this.clansDB.create(ownerId, name);

        Member member = new Member(
                // member name
                Server.getInstance()
                        .getPlayer(ownerId)
                        .map(Player::getName)
                        .orElse(""),
                // member id
                ownerId,
                // member role
                Role.OWNER,
                // member join time
                System.currentTimeMillis(),
                // initial member stats,
                new MemberStats()
        );

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
        if (uuid == null)
            return Optional.empty();

        Optional<Clan> clan = Optional.ofNullable(clanCache.getIfPresent(uuid));
        if (clan.isEmpty()) {
            clan = clansDB.getClans()
                    .stream()
                    .filter(c -> c.getOwnerId().equals(uuid) || c.getMembers()
                            .containsKey(uuid))
                    .findFirst();
            clan.ifPresent(value -> clanCache.put(uuid, value));
        }
        return clan;
    }

    public Optional<Clan> getClanWithName(String name) {
        Server server = Server.getInstance();
        IPlayer player = server.getOfflinePlayer(name);
        if (player == null || player.getUniqueId() == null)
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

        damagerClan.ifPresent((c) -> damagerHandle(killer, c));

        victimClan.ifPresent((c) -> victimHandle(victim, c));


    }

    private void damagerHandle(UUID damagerId, Clan damagerClan) {
        int initialClanExp = damagerClan.getStats().getExp();

        var stats = damagerClan.getStats();
        stats.setExp(stats.getExp() + 1);

        var damagerMemberStats = damagerClan.getMembers()
                .get(damagerId).getMemberStats();
        damagerMemberStats.setKills(damagerMemberStats.getKills() + 1);

        int finallyClanExp = damagerClan.getStats().getExp();
        onChangeExp(damagerClan, initialClanExp, finallyClanExp);
    }

    public void onChangeExp(Clan clan, int initExp, int newExp) {
        Level initLevel = getLevel(initExp);
        Level finalLevel = getLevel(newExp);
        String message = initExp < newExp ? "Clan level upgrade"
                : "Clan level downgraded";
        if (!initLevel.equals(finalLevel)) {
            clan.getMembers()
                    .forEach((memberId, __) ->
                            Server.getInstance().getPlayer(memberId)
                                    .ifPresent(p -> p.sendMessage(message)));
        }
    }

    private void victimHandle(UUID victimId, Clan victimClan) {
        int initialClanExp = victimClan.getStats().getExp();

        var stats = victimClan.getStats();
        stats.setExp(stats.getExp() - 1);

        var victimMemberStats = victimClan.getMembers()
                .get(victimId).getMemberStats();
        victimMemberStats.setDeath(victimMemberStats.getDeath() + 1);
        int finallyClanExp = victimClan.getStats().getExp();
        onChangeExp(victimClan, initialClanExp, finallyClanExp);
    }
}
