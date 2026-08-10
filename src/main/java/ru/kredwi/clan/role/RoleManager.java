package ru.kredwi.clan.role;

import lombok.RequiredArgsConstructor;
import ru.kredwi.clan.model.Clan;

@RequiredArgsConstructor
public class RoleManager {

    private final Clan clan;

    public void addRole(Role role) {

    }

    public Role getRole(String roleName) {
        return new Role();
    }
}
