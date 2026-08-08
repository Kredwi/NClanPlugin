package ru.kredwi.clan.model;

import java.util.UUID;

public record RequestData(UUID requestor, UUID requested) {
}
