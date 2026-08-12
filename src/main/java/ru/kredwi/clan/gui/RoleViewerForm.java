package ru.kredwi.clan.gui;

import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.role.Role;
import ru.kredwi.clan.service.MessagesService;

@Getter
public class RoleViewerForm extends Form {
    private final FormWindow form;

    public RoleViewerForm(MessagesService messagesService, @NonNull Role role) {
        this.form = new FormWindowSimple(
                messagesService.getMessage("clan.form.role.viewer.title", role.getName()),
                messagesService.getMessage("clan.form.role.viewer.body",
                        role.getPriority(), role.getName(), String.join("\n", role.getPermissions())));
    }
}
