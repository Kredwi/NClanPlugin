package ru.kredwi.clan.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.AllArgsConstructor;
import ru.kredwi.clan.model.RequestData;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class RequestService {

    private final Cache<UUID, RequestData> requests = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    private final ClanService clanService;

    public void createRequest(RequestData requestData) {
        this.requests.put(requestData.requested(), requestData);
    }

    public void remove(UUID requestor) {
        this.requests.invalidate(requestor);
    }

    public Optional<RequestData> getRequested(UUID requsted) {
        return Optional.ofNullable(this.requests.getIfPresent(requsted));
    }

    public void clear() {
        this.requests.cleanUp();
    }

}
