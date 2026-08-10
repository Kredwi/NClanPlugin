package ru.kredwi.clan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.DefaultRoles;
import ru.kredwi.clan.role.Role;

import java.util.UUID;

@AllArgsConstructor
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Member {
    @NonNull
    private String displayName;
    @NonNull
    private UUID id;
    @NonNull
    private Role role = DefaultRoles.MEMBER;
    private long joinAt = System.currentTimeMillis();
    private MemberStats memberStats;
}


