package ru.kredwi.clan.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Role {

    private String name;
    private int priority;

    private List<String> permissions;

    public Role(Role role) {
        this.name = role.getName();
        this.priority = role.getPriority();
        this.permissions = role.getPermissions();
    }
}
