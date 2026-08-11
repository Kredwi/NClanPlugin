package ru.kredwi.clan.gui;

import cn.nukkit.IPlayer;
import cn.nukkit.Server;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.model.Member;
import ru.kredwi.clan.model.MemberStats;
import ru.kredwi.clan.service.MessagesService;

public class PlayerStatsForm extends Form {

    private final FormWindow window;

    public PlayerStatsForm(MessagesService messagesService, @NonNull Member member) {

        MemberStats stats = member.getMemberStats();
        IPlayer player = Server.getInstance().getOfflinePlayer(member.getId());
        String msgError = messagesService.getMessage("clan.form.error.title");
        if (stats == null) {
            this.window = new FormWindowSimple(msgError, messagesService.getMessage("clan.form.error.stats_not_found"));
            return;
        }

        if (player == null) {
            this.window = new FormWindowSimple(msgError, messagesService.getMessage("clan.form.error.player_not_found"));
            return;
        }

        this.window = new FormWindowSimple(messagesService.getMessage("clan.info.stats_header", member.getDisplayName()),
                messagesService.getMessage("clan.info.stats_body",
                        member.getDisplayName(),
                        stats.getKills(),
                        player.isOnline(), // TODO override to "disable" "enable" or "yes" "no"
                        member.getRole().getName(),
                        player.getLastPlayed()));

    }

    @Override
    public @NonNull FormWindow getForm() {
        return window;
    }
}
