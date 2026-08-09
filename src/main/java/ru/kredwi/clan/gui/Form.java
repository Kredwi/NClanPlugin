package ru.kredwi.clan.gui;

import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindow;
import org.jspecify.annotations.NonNull;

public abstract class Form {
    public void showForm(Player player) {
        player.closeFormWindows();
        player.showFormWindow(getForm());
    }

    public abstract @NonNull FormWindow getForm();
}
