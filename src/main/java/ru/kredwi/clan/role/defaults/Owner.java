package ru.kredwi.clan.role.defaults;

import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.role.Role;

import java.util.Arrays;

public class Owner extends Role {
    public Owner() {
        super("Owner", 5, Arrays
                .stream(ClanPermissions.values())
                .map(ClanPermissions::getPermission).toList());
    }
}
