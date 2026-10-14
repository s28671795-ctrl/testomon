/*
 * Decompiled with CFR 0.152.
 */
package io.minio.credentials;

import io.minio.credentials.AssumeRoleBaseProvider;
import io.minio.credentials.Jwt;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public abstract class WebIdentityClientGrantsProvider
extends AssumeRoleBaseProvider {
    public static final int MIN_DURATION_SECONDS = (int)TimeUnit.MINUTES.toSeconds(15L);
    public static final int MAX_DURATION_SECONDS = (int)TimeUnit.DAYS.toSeconds(7L);
    private static final RequestBody EMPTY_BODY = RequestBody.create(new byte[0], MediaType.parse("application/octet-stream"));
    private final Supplier<Jwt> supplier;
    protected final HttpUrl stsEndpoint;
    protected final Integer durationSeconds;
    protected final String policy;
    protected final String roleArn;
    protected final String roleSessionName;

    public WebIdentityClientGrantsProvider(@Nonnull Supplier<Jwt> supplier, @Nonnull String stsEndpoint, @Nullable Integer durationSeconds, @Nullable String policy, @Nullable String roleArn, @Nullable String roleSessionName, @Nullable OkHttpClient customHttpClient) {
        super(customHttpClient);
        this.supplier = Objects.requireNonNull(supplier, "JWT token supplier must not be null");
        stsEndpoint = Objects.requireNonNull(stsEndpoint, "STS endpoint cannot be empty");
        this.stsEndpoint = Objects.requireNonNull(HttpUrl.parse(stsEndpoint), "Invalid STS endpoint");
        this.durationSeconds = durationSeconds;
        this.policy = policy;
        this.roleArn = roleArn;
        this.roleSessionName = roleSessionName;
    }

    protected int getDurationSeconds(int expiry) {
        if (this.durationSeconds != null && this.durationSeconds > 0) {
            expiry = this.durationSeconds;
        }
        if (expiry > MAX_DURATION_SECONDS) {
            return MAX_DURATION_SECONDS;
        }
        if (expiry <= 0) {
            return expiry;
        }
        return expiry < MIN_DURATION_SECONDS ? MIN_DURATION_SECONDS : expiry;
    }

    @Override
    protected Request getRequest() {
        Jwt jwt = this.supplier.get();
        HttpUrl.Builder urlBuilder = this.newUrlBuilder(jwt);
        return new Request.Builder().url(urlBuilder.build()).method("POST", EMPTY_BODY).build();
    }

    protected abstract HttpUrl.Builder newUrlBuilder(Jwt var1);
}

