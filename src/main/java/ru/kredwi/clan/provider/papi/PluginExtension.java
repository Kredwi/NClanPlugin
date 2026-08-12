package ru.kredwi.clan.provider.papi;

import cn.nukkit.Player;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import ru.kredwi.clan.NClanPlugin;
import ru.kredwi.clan.service.ClanService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class PluginExtension {

    public static final Set<String> ALIASES = Set.of("nclan", "nclanplugin");
    public static final Map<String, BiFunction<List<String>, Player, String>> PARAMS = new HashMap<>();

    public PluginExtension(ClanService clanService) {
//        PlaceholderAPI.getInstance().builder("clan", String.class)
//                .aliases(ALIASES.toArray(new String[0]))
//                .autoUpdate(true)
//                .updateInterval(1000)
//                .processParameters(true)
//                .visitorLoader(entry -> {
                    // intellij idea cannot be compile the code
                    // but the compiled by mvn package command
                    // idk
//                    String fullPlaceholder = entry.getMatchedGroup().getPlaceholder();
//                    String[] splitedPlaceholders = fullPlaceholder.split("_");
//                    if (splitedPlaceholders.length > 0) {
//                        var param = PARAMS.get(splitedPlaceholders[1]);
//                        if (param == null)
//                            return "";
//
//                        var params = List.of(splitedPlaceholders);
//
//                        return param.apply(params, entry.getPlayer());
//                    }
//                    NClanPlugin.log.info("placeholder with name " + fullPlaceholder + " is not found");
//                    return "";
//                })
//                .build();
        // TODO shit (i love original papi. PAPI 1.4 for nukkit the fucking shit in kotlin)
    }
}
