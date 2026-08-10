package ru.kredwi.clan.gui;

import cn.nukkit.form.element.*;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.Permissions;

public class RoleManagerForm extends Form{
    private final FormWindow form;

    public RoleManagerForm(@NonNull Clan clan) {
        FormWindowCustom form = new FormWindowCustom("Role Manager");
        clan.getRoles().forEach((r) -> {
            form.addElement(new ElementHeader(r.getName()));
            form.addElement(new ElementInput("name", "role name", r.getName()));
            form.addElement(new ElementInput("\npriority", "priority", String.valueOf(r.getPriority())));
            form.addElement(new ElementLabel("\n\nPermissions"));

            for (Permissions perm : Permissions.values()) {
                form.addElement(new ElementToggle(perm.getPermission().getName(), r.getPermissions().contains(perm.getPermission())));
            }
            form.addElement(new ElementDivider());
            form.addElement(new ElementLabel("\n\n"));
        });

        this.form = form;
    }

    @Override
    public @NonNull FormWindow getForm() {
        return form;
    }
}
