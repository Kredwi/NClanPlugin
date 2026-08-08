package ru.kredwi.clan.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import lombok.RequiredArgsConstructor;
import ru.kredwi.clan.service.ClanService;

import java.util.UUID;

@RequiredArgsConstructor
public class PlayerKillPlayer implements Listener {

    private final ClanService clanService;

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent event) {
            if (event.getDamager() instanceof Player damagerPlayer) {
                UUID damagerId = damagerPlayer.getUniqueId();
                UUID victimId = player.getUniqueId();

                clanService.onPlayerKill(damagerId, victimId);
            }
        }
    }

}
