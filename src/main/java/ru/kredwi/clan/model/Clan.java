package ru.kredwi.clan.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.DefaultRoles;
import ru.kredwi.clan.role.Role;

import java.util.HashMap;
import java.util.List;
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

    @NonNull
    private Map<UUID, Member> members;

    @NonNull
    private List<Role> roles;

    @NonNull
    public static Clan of(int id, UUID ownerId, String name) {
        return new Clan(id, ownerId, name, new ClanStats(), new ClanSettings(), new HashMap<>(), List.of(
                new Role(DefaultRoles.DEFAULT),
                new Role(DefaultRoles.MEMBER),
                new Role(DefaultRoles.MODER),
                new Role(DefaultRoles.OWNER)
        ));
    }

    @NonNull
    public static Clan of(int id, UUID ownerId, String name, ClanStats clanStats, ClanSettings settings, Map<UUID, Member> members, List<ru.kredwi.clan.role.Role> roles) {
        return new Clan(id, ownerId, name, clanStats, settings, members, roles);
    }
}
