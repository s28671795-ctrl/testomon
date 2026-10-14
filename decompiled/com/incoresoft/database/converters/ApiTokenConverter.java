/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database.converters;

import com.incoresoft.generated.tables.records.ApiTokensRecord;
import com.incoresoft.sessions.api.ApiToken;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;

public class ApiTokenConverter
implements Converter<ApiTokensRecord, ApiToken> {
    @Override
    public ApiToken from(ApiTokensRecord databaseObject) {
        return new ApiToken(databaseObject.getId(), databaseObject.getName(), databaseObject.getApiKey(), databaseObject.getApiKeyHash(), databaseObject.getUserId(), databaseObject.getIssuedAt(), databaseObject.getExpiresAt());
    }

    @Override
    public ApiTokensRecord to(ApiToken userObject) {
        return new ApiTokensRecord(userObject.id(), userObject.name(), userObject.userId(), userObject.apiKey(), userObject.apiKeyHash(), userObject.issuedAt(), userObject.expiresAt());
    }

    @Override
    @NotNull
    public Class<ApiTokensRecord> fromType() {
        return ApiTokensRecord.class;
    }

    @Override
    @NotNull
    public Class<ApiToken> toType() {
        return ApiToken.class;
    }
}

