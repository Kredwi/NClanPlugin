package ru.kredwi.clan.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import ru.kredwi.clan.model.RequestData;
import ru.kredwi.clan.provider.ConfigProvider;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RequestService {

    private final Cache<UUID, RequestData> requests;

    public RequestService(ConfigProvider config) {
        this.requests = CacheBuilder.newBuilder()
                .expireAfterWrite(config.getRequestExpireMinutes(), TimeUnit.MINUTES)
                .build();
    }

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
