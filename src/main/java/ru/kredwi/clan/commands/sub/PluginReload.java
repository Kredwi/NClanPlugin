package ru.kredwi.clan.commands.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.NClanPlugin;
import ru.kredwi.clan.api.command.SubCommand;
import ru.kredwi.clan.model.Services;
import ru.kredwi.clan.permission.CommonPermissions;

import java.util.List;
import java.util.TreeMap;

@AllArgsConstructor
public class PluginReload implements SubCommand {

    private Services services;

    @Override
    public void onCommand(@NonNull CommandSender sender, @NonNull List<String> args) {
        services.messagesService().reload();
        NClanPlugin.log.debug("Messages reloaded");
        services.configProvider().reload();
        NClanPlugin.log.debug("Config reloaded");
        services.clanShop().setItems(services.files().clanShopFile().read());
        NClanPlugin.log.debug("Clan shop reloaded");
        services.levelService().setLevels(new TreeMap<>(services.files().levelFile().read()));
        NClanPlugin.log.debug("Levels reloaded");
        services.messagesService().sendMessage(sender, "clan.command.reload.success");
    }

    @Override
    public @NonNull Permission getPermission() {
        return CommonPermissions.CLAN_ADMIN_PERMISSION.getPermission();
    }
}
