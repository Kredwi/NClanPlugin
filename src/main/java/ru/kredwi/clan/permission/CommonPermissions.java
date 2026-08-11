package ru.kredwi.clan.permission;

import cn.nukkit.permission.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonPermissions {
    CLAN_ADMIN_PERMISSION(new Permission("clan.admin")),
    CLAN_USE_PERMISSION(new Permission("clan.use"));

    private final Permission permission;

}
