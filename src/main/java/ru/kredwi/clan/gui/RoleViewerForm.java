package ru.kredwi.clan.gui;

import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.permission.Permission;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.role.Role;

import java.util.stream.Collectors;

@Getter
public class RoleViewerForm extends Form {
    private final FormWindow form;

    public RoleViewerForm(@NonNull Role role) {
        this.form = new FormWindowSimple(
                "Info of role " + role.getName(),
                "\n\n\nPriority " + role.getPriority() +
                        "\n\nName " + role.getName() +
                        "\n\nPermissions: \n" + role.getPermissions()
                        .stream()
                        .map(Permission::getName)
                        .collect(Collectors.joining("\n")));
    }
}
