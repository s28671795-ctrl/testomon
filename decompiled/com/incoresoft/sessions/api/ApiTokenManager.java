/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.sessions.api;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.Inject;
import com.incoresoft.database.ApiTokenRepository;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.sessions.api.ApiToken;
import com.incoresoft.sessions.api.ApiTokenActionsListener;
import com.incoresoft.sessions.api.TokenGenerator;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.pac4j.core.credentials.password.JBCryptPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiTokenManager {
    private static final Logger log = LoggerFactory.getLogger(ApiTokenManager.class);
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(Thread.ofVirtual().factory());
    private final Set<ApiTokenActionsListener> listeners = Collections.newSetFromMap(new ConcurrentHashMap());
    private final Cache<String, ApiToken> validTokens = CacheBuilder.newBuilder().expireAfterWrite(Duration.ofSeconds(30L)).maximumSize(10000L).build();
    private final ApiTokenRepository repository;

    @Inject
    public ApiTokenManager(@NotNull ApiTokenRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Inject
    public void startExpiredTokensCleaning() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime nextCleaningPassTime = now.plusDays(1L).truncatedTo(ChronoUnit.DAYS);
        this.executorService.scheduleAtFixedRate(this::removeExpiredTokens, Duration.between(now, nextCleaningPassTime).toMillis(), Duration.ofDays(1L).toMillis(), TimeUnit.MILLISECONDS);
        log.info("Scheduled task to clean up expired tokens will run at {}", (Object)nextCleaningPassTime);
    }

    private void removeExpiredTokens() {
        for (ApiToken token : this.repository.getAndRemoveExpired()) {
            this.triggerListeners(listener -> listener.onApiTokenExpired(token));
        }
    }

    public void addListener(ApiTokenActionsListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(ApiTokenActionsListener listener) {
        this.listeners.remove(listener);
    }

    public List<ApiToken> get(int userId) {
        return this.repository.get(userId);
    }

    public Optional<ApiToken> checkApiKey(String apiKey) {
        ApiToken cached = this.validTokens.getIfPresent(apiKey);
        if (cached != null && cached.valid()) {
            return Optional.of(cached);
        }
        Optional<ApiToken> token = this.repository.checkApiKey(apiKey);
        token.ifPresent(it -> this.validTokens.put(apiKey, (ApiToken)it));
        return token;
    }

    public Pair<String, ApiToken> issue(int userId, String name, Duration validFor) {
        String apiKey = TokenGenerator.nextToken();
        String apiKeyHash = new JBCryptPasswordEncoder().encode(apiKey);
        ApiToken apiToken = this.repository.create(userId, name, apiKey, apiKeyHash, validFor);
        this.triggerListeners(listener -> listener.onApiTokenIssued(apiToken));
        return Pair.of(apiKey, apiToken);
    }

    public void revoke(User user, int tokenId) {
        Optional<ApiToken> token = this.repository.getById(tokenId);
        this.repository.delete(user.getId(), tokenId);
        this.validTokens.asMap().values().removeIf(t2 -> t2.id() == tokenId && t2.userId() == user.getId());
        if (token.isPresent()) {
            this.triggerListeners(listener -> listener.onApiTokenRevoked((ApiToken)token.orElseThrow()));
        }
    }

    public void revokeAll(int userId) {
        List<ApiToken> userTokens = this.repository.get(userId);
        this.repository.deleteAll(userId);
        this.validTokens.asMap().values().removeIf(t2 -> t2.userId() == userId);
        for (ApiToken token : userTokens) {
            this.triggerListeners(listener -> listener.onApiTokenRevoked(token));
        }
    }

    public boolean checkSession(String apiKey) {
        if (apiKey == null) {
            return false;
        }
        Optional<ApiToken> result = this.checkApiKey(apiKey);
        if (result.isEmpty()) {
            return false;
        }
        return result.orElseThrow().valid();
    }

    private void triggerListeners(Consumer<ApiTokenActionsListener> action) {
        for (ApiTokenActionsListener listener : this.listeners) {
            try {
                action.accept(listener);
            }
            catch (Exception e) {
                log.error("Error while triggering listener", e);
            }
        }
    }
}

