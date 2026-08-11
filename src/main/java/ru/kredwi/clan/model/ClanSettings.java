package ru.kredwi.clan.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import ru.kredwi.clan.utils.Location;

import java.util.Optional;

@Data
@NoArgsConstructor
public class ClanSettings {
    @Nullable
    private Location clanHome;
    private boolean pvp;

    public Optional<Location> getClanHome() {
        return Optional.ofNullable(clanHome);
    }
}
