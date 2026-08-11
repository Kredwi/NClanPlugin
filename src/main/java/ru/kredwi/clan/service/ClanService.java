package ru.kredwi.clan.service;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import it.unimi.dsi.fastutil.Pair;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.api.db.ClanDB;
import ru.kredwi.clan.model.*;
import ru.kredwi.clan.provider.ConfigProvider;
import ru.kredwi.clan.role.Role;
import ru.kredwi.clan.role.defaults.Owner;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ClanService {

    private final ConfigProvider config;
    private final LevelService levelService;
    private final ClanDB clansDB;

    public Level getLevel(int exp) {
        return this.levelService.getLevel(exp);
    }

    @NonNull
    public Clan create(UUID ownerId, String name) {
        Clan clan = this.clansDB.create(ownerId, name);
        Role role;
        if (clan.getRoles().isEmpty()) {
            role = new Owner();
            clan.setRoles(List.of(role));
        } else role = clan.getRoles().get(clan.getRoles().size() - 1);

        clan.getSettings().setPvp(config.isClanPvpDefault());
        clan.getStats().setExp(config.getDefaultClanExp());
        clan.getStats().setBalance(config.getDefaultClanBalance());

        Member member = new Member(
                // member name
                Server.getInstance()
                        .getPlayer(ownerId)
                        .map(Player::getName)
                        .orElse(""),
                // member id
                ownerId,
                // member role
                role,
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

    public void remove(UUID clanId) {
        this.clansDB.remove(clanId);
    }

    public Optional<Clan> get(UUID id) {
        return this.clansDB.get(id);
    }

    public Optional<ClanStats> getClanStats(UUID id) {
        return this.clansDB.get(id)
                .map(Clan::getStats);
    }

    public Optional<Clan> getClanWithUUID(UUID uuid) {
        if (uuid == null)
            return Optional.empty();

        return clansDB.getClans()
                .stream()
                .filter(c -> c.getOwnerId().equals(uuid) || c.getMembers()
                        .containsKey(uuid))
                .findFirst();
    }

    public Optional<Clan> getClanWithName(String name) {
        Server server = Server.getInstance();
        IPlayer player = server.getOfflinePlayer(name);
        if (player == null || player.getUniqueId() == null)
            return Optional.empty();
        return this.getClanWithUUID(player.getUniqueId());
    }

    public Optional<Set<Pair<UUID, Member>>> getMembers(UUID id) {
        return this.clansDB.get(id)
                .map(Clan::getMembers)
                .map(e -> e.entrySet().stream()
                        .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toSet()));
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
}
