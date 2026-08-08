package ru.kredwi.clan.listener;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import lombok.RequiredArgsConstructor;
import ru.kredwi.clan.service.ClanService;

@RequiredArgsConstructor
public class PlayerDamageEvent implements Listener {

    private final ClanService clanService;

    @EventHandler(
            priority = EventPriority.LOW
    )
    public void onDamage(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        Entity entity = e.getEntity();

        if ((damager instanceof Player damagerPlayer && entity instanceof Player damagerEntity)) {
            var damagerClan = clanService.getClanWithUUID(damagerPlayer.getUniqueId());
            var entityClan = clanService.getClanWithUUID(damagerEntity.getUniqueId());

            if (damagerClan.isPresent() && entityClan.isPresent()) {
                if (damagerClan.get().equals(entityClan.get())
                        && !damagerClan.get().getSettings().isPvp())
                    e.setCancelled();
            }
        }
    }

}
