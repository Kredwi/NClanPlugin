package ru.kredwi.clan.gui;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementDivider;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import lombok.Getter;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.role.Role;

public class RoleCreateForm extends FormWindowCustom {

    @Getter
    private final Clan clan;

    public RoleCreateForm(Clan clan) {
        super("Role creator");

        this.clan = clan;

        Role r = new Role();

        addElement(new ElementInput("name", "role name", r.getName()));
        addElement(new ElementInput("\npriority", "priority", String.valueOf(r.getPriority())));
        addElement(new ElementDivider());
        addElement(new ElementLabel("\nPermissions"));
        for (ClanPermissions perm : ClanPermissions.values()) {
            addElement(new ElementToggle(perm.getPermission().getName()));
        }
    }

    public void showForm(Player player) {
        player.closeFormWindows();
        player.showFormWindow(getForm());
    }

    public FormWindow getForm() {
        return this;
    }
}
