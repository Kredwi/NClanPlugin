package ru.kredwi.clan.role;

import cn.nukkit.permission.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Role {

    private String name;
    private int priority;

    private List<Permission> permissions;

    public Role(Role role) {
        this.name = role.getName();
        this.priority = role.getPriority();
        this.permissions = role.getPermissions();
    }
}
