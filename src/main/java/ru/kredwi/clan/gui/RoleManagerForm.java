package ru.kredwi.clan.gui;

import cn.nukkit.Player;
import cn.nukkit.form.element.*;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.role.Role;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RoleManagerForm extends FormWindowCustom {
    private final FormWindow form;
    private final List<Role> roles;

    public RoleManagerForm(@NonNull Clan clan) {
        super("Role Manager");

        this.roles = new ArrayList<>(clan.getRoles());

        roles.forEach((r) -> {
            addElement(new ElementHeader(r.getName()));
            addElement(new ElementInput("name", "role name", r.getName()));
            addElement(new ElementInput("\npriority", "priority", String.valueOf(r.getPriority())));
            addElement(new ElementLabel("\n\nPermissions"));

            for (ClanPermissions perm : ClanPermissions.values()) {
                addElement(new ElementToggle(perm.getPermission().getName(), r.getPermissions().contains(perm.getPermission())));
            }

            addElement(new ElementDivider());
            addElement(new ElementLabel("\n\n"));
        });

        this.form = this;
    }

    public void showForm(Player player) {
        player.closeFormWindows();
        player.showFormWindow(getForm());
    }
}
