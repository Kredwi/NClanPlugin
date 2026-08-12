package ru.kredwi.clan.utils;

import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.NClanPlugin;

import java.util.function.Supplier;

public class DependenciesLoader<T> {

    public @NonNull T loadDepend(@NonNull String pluginName, @NonNull Supplier<? extends T> s, @NonNull T emptyInstance) {
        Plugin plugin = Server.getInstance().getPluginManager()
                .getPlugin(pluginName);

        if (plugin != null && plugin.isEnabled()) {
            log(pluginName, "Dependencies with name " + pluginName + " is loaded.");
            return s.get();
        } else {
            log(pluginName, "Dependencies with name " + pluginName + " is not loaded.");
            return emptyInstance;
        }
    }

    private void log(String dependName, String message) {
        NClanPlugin.log.debug("[DEPEND ][" + dependName + "] " + message);
    }

}
