package ru.kredwi.clan.events;

import lombok.AllArgsConstructor;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.provider.ConfigProvider;
import ru.kredwi.clan.service.ClanService;

import java.util.UUID;

@AllArgsConstructor
public class DamageEvent {

    private final ConfigProvider provider;
    private final ClanService clanService;

    public void onPlayerKill(UUID killer, UUID victim) {
        var damagerClan = clanService.getClanWithUUID(killer);
        var victimClan = clanService.getClanWithUUID(victim);

        if (damagerClan.isPresent() && victimClan.isPresent()) {
            if (damagerClan.get().getId().equals(victimClan.get().getId()))
                return;
        }

        damagerClan.ifPresent((c) -> damagerHandle(killer, c));

        victimClan.ifPresent((c) -> victimHandle(victim, c));

    }

    private void damagerHandle(UUID damagerId, Clan damagerClan) {
        int initialClanExp = damagerClan.getStats().getExp();

        var stats = damagerClan.getStats();
        stats.setExp(stats.getExp() + this.provider.getExpPerKill());

        var damagerMemberStats = damagerClan.getMembers()
                .get(damagerId).getMemberStats();
        damagerMemberStats.setKills(damagerMemberStats.getKills() + 1);

        int finallyClanExp = damagerClan.getStats().getExp();
        clanService.onChangeExp(damagerClan, initialClanExp, finallyClanExp);
    }

    private void victimHandle(UUID victimId, Clan victimClan) {
        int initialClanExp = victimClan.getStats().getExp();

        var stats = victimClan.getStats();
        int newExp = stats.getExp() - this.provider.getExpPerDeath();
        if (newExp < 0)
            newExp = 0;
        stats.setExp(newExp);

        var victimMemberStats = victimClan.getMembers()
                .get(victimId).getMemberStats();
        victimMemberStats.setDeath(victimMemberStats.getDeath() + 1);
        int finallyClanExp = victimClan.getStats().getExp();
        clanService.onChangeExp(victimClan, initialClanExp, finallyClanExp);
    }

}
