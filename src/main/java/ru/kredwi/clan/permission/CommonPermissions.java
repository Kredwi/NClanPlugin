package ru.kredwi.clan.permission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonPermissions {
    CLAN_ADMIN_PERMISSION("clan.admin"),
    CLAN_USE_PERMISSION("clan.use");

    private final String permission;

}
