/*
 * Decompiled with CFR 0.152.
 */
package io.minio.credentials;

import io.minio.credentials.AssumeRoleBaseProvider;
import io.minio.credentials.Credentials;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

public class CertificateIdentityProvider
extends AssumeRoleBaseProvider {
    private static final RequestBody EMPTY_BODY = RequestBody.create(new byte[0], MediaType.parse("application/octet-stream"));
    private final Request request;

    public CertificateIdentityProvider(@Nonnull String stsEndpoint, @Nullable SSLSocketFactory sslSocketFactory, @Nullable X509TrustManager trustManager, @Nullable Integer durationSeconds, @Nullable OkHttpClient customHttpClient) throws GeneralSecurityException, IOException {
        super(customHttpClient, sslSocketFactory, trustManager);
        stsEndpoint = Objects.requireNonNull(stsEndpoint, "STS endpoint cannot be empty");
        HttpUrl url2 = Objects.requireNonNull(HttpUrl.parse(stsEndpoint), "Invalid STS endpoint");
        if (!url2.isHttps()) {
            throw new IllegalArgumentException("STS endpoint scheme must be HTTPS");
        }
        HttpUrl.Builder urlBuilder = this.newUrlBuilder(url2, "AssumeRoleWithCertificate", CertificateIdentityProvider.getValidDurationSeconds(durationSeconds), null, null, null);
        url2 = urlBuilder.build();
        this.request = new Request.Builder().url(url2).method("POST", EMPTY_BODY).build();
    }

    @Override
    protected Request getRequest() {
        return this.request;
    }

    @Override
    protected Class<? extends AssumeRoleBaseProvider.Response> getResponseClass() {
        return CertificateIdentityResponse.class;
    }

    @Root(name="AssumeRoleWithCertificateResponse", strict=false)
    @Namespace(reference="https://sts.amazonaws.com/doc/2011-06-15/")
    public static class CertificateIdentityResponse
    implements AssumeRoleBaseProvider.Response {
        @Path(value="AssumeRoleWithCertificateResult")
        @Element(name="Credentials")
        private Credentials credentials;

        @Override
        public Credentials getCredentials() {
            return this.credentials;
        }
    }
}

