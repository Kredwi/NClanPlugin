package ru.kredwi.clan.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class Clan {

    private final int id;
    private final UUID ownerId;
    private final String name;

    @NonNull
    private ClanStats stats;
    @NonNull
    private ClanSettings settings;
    // owner cannot be enumerated in members
    @NonNull
    private Map<UUID, Role> members;

    @NonNull
    public static Clan of(int id, UUID ownerId, String name) {
        return new Clan(id, ownerId, name, new ClanStats(), new ClanSettings(), new HashMap<>());
    }
    @NonNull
    public static Clan of(int id, UUID ownerId, String name, ClanStats clanStats, ClanSettings settings, Map<UUID, Role> members) {
        return new Clan(id, ownerId, name, clanStats, settings, members);
    }
}
