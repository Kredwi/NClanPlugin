package ru.kredwi.clan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kredwi.clan.permission.Permissions;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public enum Role {

    OWNER("Owner", new ArrayList<>()),
    MODERATOR("Moder", new ArrayList<>()),
    DEFAULT("Member", new ArrayList<>()); // the member

    private final String displayName;
    @Getter
    private final List<Permissions> permissions;

}
