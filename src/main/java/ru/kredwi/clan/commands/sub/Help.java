package ru.kredwi.clan.commands.sub;

import cn.nukkit.command.CommandSender;
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
    }

    @Override
    public @NonNull Permission getPermission() {
        return Permissions.PERMISSION_CLAN_HELP.getPermission();
    }
}
