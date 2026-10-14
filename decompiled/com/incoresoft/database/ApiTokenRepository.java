/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database;

import com.google.inject.Inject;
import com.incoresoft.database.converters.ApiTokenConverter;
import com.incoresoft.generated.Tables;
import com.incoresoft.generated.tables.records.ApiTokensRecord;
import com.incoresoft.sessions.api.ApiToken;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.jooq.DSLContext;
import org.pac4j.core.credentials.password.JBCryptPasswordEncoder;

public class ApiTokenRepository {
    private final DSLContext context;
    private final ApiTokenConverter converter;

    public ApiToken create(int userId, String name, String apiKey, String apiKeyHash, Duration validFor) {
        ApiTokensRecord record = this.converter.to(new ApiToken(userId, name, apiKey, apiKeyHash, validFor));
        record.reset(Tables.API_TOKENS.ID);
        int sessionId = this.context.insertInto(Tables.API_TOKENS).set(record).returning(Tables.API_TOKENS.ID).fetchSingle(0, Integer.TYPE);
        return this.getById(sessionId).orElseThrow();
    }

    public Optional<ApiToken> getById(int id) {
        return this.context.selectFrom(Tables.API_TOKENS).where(Tables.API_TOKENS.ID.eq(id)).and(Tables.API_TOKENS.EXPIRES_AT.greaterThan(Instant.now())).fetchOptional(this.converter::from);
    }

    public List<ApiToken> get(int userId) {
        return this.context.selectFrom(Tables.API_TOKENS).where(Tables.API_TOKENS.USER_ID.eq(userId)).and(Tables.API_TOKENS.EXPIRES_AT.greaterThan(Instant.now())).fetch(this.converter::from);
    }

    public Optional<ApiToken> checkApiKey(String apiKey) {
        JBCryptPasswordEncoder bcryptEncoder = new JBCryptPasswordEncoder();
        return this.context.selectFrom(Tables.API_TOKENS).where(Tables.API_TOKENS.API_KEY.eq(apiKey)).and(Tables.API_TOKENS.EXPIRES_AT.greaterThan(Instant.now())).fetch(this.converter::from).stream().filter(it -> bcryptEncoder.matches(apiKey, it.apiKeyHash())).findAny();
    }

    public void deleteAll(int userId) {
        this.deleteAll(userId, this.context);
    }

    public void deleteAll(int userId, DSLContext transaction) {
        transaction.deleteFrom(Tables.API_TOKENS).where(Tables.API_TOKENS.USER_ID.eq(userId)).execute();
    }

    public void delete(int userId, int tokenId) {
        this.context.deleteFrom(Tables.API_TOKENS).where(Tables.API_TOKENS.USER_ID.eq(userId)).and(Tables.API_TOKENS.ID.eq(tokenId)).execute();
    }

    public List<ApiToken> getAndRemoveExpired() {
        Instant now = Instant.now();
        List<ApiToken> expiredTokens = this.context.selectFrom(Tables.API_TOKENS).where(Tables.API_TOKENS.EXPIRES_AT.lessThan(now)).fetch(this.converter::from);
        this.context.deleteFrom(Tables.API_TOKENS).where(Tables.API_TOKENS.EXPIRES_AT.lessThan(now)).execute();
        return expiredTokens;
    }

    @Inject
    public ApiTokenRepository(DSLContext context, ApiTokenConverter converter) {
        this.context = context;
        this.converter = converter;
    }
}

