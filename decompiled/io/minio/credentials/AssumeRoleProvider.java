/*
 * Decompiled with CFR 0.152.
 */
package io.minio.credentials;

import io.minio.Digest;
import io.minio.Signer;
import io.minio.Time;
import io.minio.credentials.AssumeRoleBaseProvider;
import io.minio.credentials.Credentials;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.ProviderException;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

public class AssumeRoleProvider
extends AssumeRoleBaseProvider {
    private final String accessKey;
    private final String secretKey;
    private final String region;
    private final String contentSha256;
    private final Request request;

    public AssumeRoleProvider(@Nonnull String stsEndpoint, @Nonnull String accessKey, @Nonnull String secretKey, @Nullable Integer durationSeconds, @Nullable String policy, @Nullable String region, @Nullable String roleArn, @Nullable String roleSessionName, @Nullable String externalId, @Nullable OkHttpClient customHttpClient) throws NoSuchAlgorithmException {
        super(customHttpClient);
        stsEndpoint = Objects.requireNonNull(stsEndpoint, "STS endpoint cannot be empty");
        HttpUrl url2 = Objects.requireNonNull(HttpUrl.parse(stsEndpoint), "Invalid STS endpoint");
        accessKey = Objects.requireNonNull(accessKey, "Access key must not be null");
        if (accessKey.isEmpty()) {
            throw new IllegalArgumentException("Access key must not be empty");
        }
        this.accessKey = accessKey;
        this.secretKey = Objects.requireNonNull(secretKey, "Secret key must not be null");
        String string = this.region = region != null ? region : "";
        if (externalId != null && (externalId.length() < 2 || externalId.length() > 1224)) {
            throw new IllegalArgumentException("Length of ExternalId must be in between 2 and 1224");
        }
        String host2 = url2.host() + ":" + url2.port();
        if (url2.scheme().equals("http") && url2.port() == 80 || url2.scheme().equals("https") && url2.port() == 443) {
            host2 = url2.host();
        }
        HttpUrl.Builder urlBuilder = this.newUrlBuilder(url2, "AssumeRole", AssumeRoleProvider.getValidDurationSeconds(durationSeconds), policy, roleArn, roleSessionName);
        if (externalId != null) {
            urlBuilder.addQueryParameter("ExternalId", externalId);
        }
        String data = urlBuilder.build().encodedQuery();
        this.contentSha256 = Digest.sha256Hash(data);
        this.request = new Request.Builder().url(url2).header("Host", host2).method("POST", RequestBody.create(data, MediaType.parse("application/x-www-form-urlencoded"))).build();
    }

    @Override
    protected Request getRequest() {
        try {
            return Signer.signV4Sts(this.request.newBuilder().header("x-amz-date", ZonedDateTime.now().format(Time.AMZ_DATE_FORMAT)).build(), this.region, this.accessKey, this.secretKey, this.contentSha256);
        }
        catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new ProviderException("Signature calculation failed", e);
        }
    }

    @Override
    protected Class<? extends AssumeRoleBaseProvider.Response> getResponseClass() {
        return AssumeRoleResponse.class;
    }

    @Root(name="AssumeRoleResponse", strict=false)
    @Namespace(reference="https://sts.amazonaws.com/doc/2011-06-15/")
    public static class AssumeRoleResponse
    implements AssumeRoleBaseProvider.Response {
        @Path(value="AssumeRoleResult")
        @Element(name="Credentials")
        private Credentials credentials;

        @Override
        public Credentials getCredentials() {
            return this.credentials;
        }
    }
}

