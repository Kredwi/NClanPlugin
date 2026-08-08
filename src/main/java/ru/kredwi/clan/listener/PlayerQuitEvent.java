package ru.kredwi.clan.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import lombok.AllArgsConstructor;
import ru.kredwi.clan.service.RequestService;

@AllArgsConstructor
public class PlayerQuitEvent implements Listener {

    private RequestService requestService;

    @EventHandler
    public void onQuit(cn.nukkit.event.player.PlayerQuitEvent e) {
        requestService
                .remove(e.getPlayer().getUniqueId());
    }

}
