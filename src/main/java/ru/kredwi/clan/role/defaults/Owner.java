package ru.kredwi.clan.role.defaults;

import ru.kredwi.clan.permission.Permissions;
import ru.kredwi.clan.role.Role;

import java.util.Arrays;

public class Owner extends Role {
    public Owner() {
        super("Owner", 5, Arrays
                .stream(Permissions.values())
                .map(Permissions::getPermission).toList());
    }
}
