/*
 * Decompiled with CFR 0.152.
 */
package io.minio.credentials;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.minio.messages.ResponseDate;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Credentials", strict=false)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Credentials {
    @Element(name="AccessKeyId")
    @JsonProperty(value="accessKey")
    private final String accessKey;
    @Element(name="SecretAccessKey")
    @JsonProperty(value="secretKey")
    private final String secretKey;
    @Element(name="SessionToken")
    @JsonProperty(value="sessionToken")
    private final String sessionToken;
    @Element(name="Expiration")
    @JsonProperty(value="expiration")
    private final ResponseDate expiration;

    public Credentials(@Nonnull @Element(name="AccessKeyId") @JsonProperty(value="accessKey") String accessKey, @Nonnull @Element(name="SecretAccessKey") @JsonProperty(value="secretKey") String secretKey, @Nullable @Element(name="SessionToken") @JsonProperty(value="sessionToken") String sessionToken, @Nullable @Element(name="Expiration") @JsonProperty(value="expiration") ResponseDate expiration) {
        this.accessKey = Objects.requireNonNull(accessKey, "AccessKey must not be null");
        this.secretKey = Objects.requireNonNull(secretKey, "SecretKey must not be null");
        if (accessKey.isEmpty() || secretKey.isEmpty()) {
            throw new IllegalArgumentException("AccessKey and SecretKey must not be empty");
        }
        this.sessionToken = sessionToken;
        this.expiration = expiration;
    }

    public String accessKey() {
        return this.accessKey;
    }

    public String secretKey() {
        return this.secretKey;
    }

    public String sessionToken() {
        return this.sessionToken;
    }

    public boolean isExpired() {
        if (this.expiration == null) {
            return false;
        }
        return ZonedDateTime.now().plus(Duration.ofSeconds(10L)).isAfter(this.expiration.zonedDateTime());
    }
}

