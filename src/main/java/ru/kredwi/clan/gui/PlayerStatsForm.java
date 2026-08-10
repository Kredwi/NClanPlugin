package ru.kredwi.clan.gui;

import cn.nukkit.IPlayer;
import cn.nukkit.Server;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.model.Member;
import ru.kredwi.clan.model.MemberStats;

import java.util.List;

public class PlayerStatsForm extends Form {

    private final FormWindow window;

    public PlayerStatsForm(@NonNull Member member) {

        MemberStats stats = member.getMemberStats();
        IPlayer player = Server.getInstance().getOfflinePlayer(member.getId());

        if (stats == null) {
            this.window = new FormWindowSimple("§cError", "§cStats for the member is not found");
            return;
        }

        if (player == null) {
            this.window = new FormWindowSimple("§cError", "§cPlayer with UUID is not found");
            return;
        }

        this.window = new FormWindowSimple("Information of member " + member.getDisplayName(),
                String.join("\n", List.of(
                        "§8 \n§8 \n§8 \n§8" + "▬".repeat(30),
                        "",
                        "§7▸ §fName: §e§l" + member.getDisplayName(),
                        "§7▸ §fKills: §c⚔ " + stats.getKills(),
                        "§7▸ §fOnline: " + player.isOnline(),
                        "§7▸ §fGroup: " + member.getRole().getName(),
                        "§7▸ §fLast played: §e⏰ " + player.getLastPlayed(),
                        "",
                        "§8" + "▬".repeat(30))
                ));

    }

    @Override
    public @NonNull FormWindow getForm() {
        return window;
    }
}
