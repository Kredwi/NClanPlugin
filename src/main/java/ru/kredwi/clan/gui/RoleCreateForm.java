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
import ru.kredwi.clan.service.MessagesService;

public class RoleCreateForm extends FormWindowCustom {

    @Getter
    private final Clan clan;

    public RoleCreateForm(MessagesService messagesService, Clan clan) {
        super(messagesService.getMessage("clan.form.role.creator.title"));

        this.clan = clan;

        Role r = new Role();

        addElement(new ElementInput(messagesService.getMessage("clan.form.role.creator.name"),
                "role name", r.getName()));
        addElement(new ElementInput("\n" + messagesService.getMessage("clan.form.role.creator.priority"),
                "priority", String.valueOf(r.getPriority())));
        addElement(new ElementDivider());
        addElement(new ElementLabel(messagesService.getMessage("clan.form.role.creator.permissions")));
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
