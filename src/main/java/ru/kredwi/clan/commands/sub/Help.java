package ru.kredwi.clan.commands.sub;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.api.command.SubCommand;
import ru.kredwi.clan.permission.Permissions;

import java.util.List;

public class Help implements SubCommand {

    @Override
    public void onCommand(@NonNull CommandSender sender, @NonNull List<String> args) {
        sender.sendMessage("======= NClanPlugin =======");
        sender.sendMessage("/clan help");
        sender.sendMessage("/clan leave");
        sender.sendMessage("/clan create <clan_name>");
        sender.sendMessage("/clan remove");
        sender.sendMessage("/clan members");
        sender.sendMessage("/clan stats <player_name>");
        sender.sendMessage("/clan kick <player_name>");
        sender.sendMessage("/clan invite <player_name>");
        sender.sendMessage("/clan accept");
        sender.sendMessage("===========================");


        var el = new ElementInput("", "penis");
        var els = new ElementLabel("text");
        var fwc = new FormWindowCustom("Пенис", List.of(el, els));
        ((Player) sender).showFormWindow(fwc);
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.USE_PERMISSION;
    }
}
