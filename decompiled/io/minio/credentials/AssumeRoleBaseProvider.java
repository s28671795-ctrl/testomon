/*
 * Decompiled with CFR 0.152.
 */
package io.minio.credentials;

import io.minio.Xml;
import io.minio.credentials.Credentials;
import io.minio.credentials.Provider;
import io.minio.errors.XmlParserException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.ProviderException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;

public abstract class AssumeRoleBaseProvider
implements Provider {
    public static final int DEFAULT_DURATION_SECONDS = (int)TimeUnit.HOURS.toSeconds(1L);
    private final OkHttpClient httpClient;
    private Credentials credentials;

    public AssumeRoleBaseProvider(OkHttpClient customHttpClient) {
        this.httpClient = customHttpClient != null ? customHttpClient : new OkHttpClient().newBuilder().protocols(Arrays.asList(Protocol.HTTP_1_1)).build();
    }

    public AssumeRoleBaseProvider(OkHttpClient customHttpClient, SSLSocketFactory sslSocketFactory, X509TrustManager trustManager) throws GeneralSecurityException, IOException {
        if (customHttpClient != null) {
            if (sslSocketFactory != null || trustManager != null) {
                throw new IllegalArgumentException("Either sslSocketFactory/trustManager or custom HTTP client must be provided");
            }
            this.httpClient = customHttpClient;
        } else {
            if (sslSocketFactory == null || trustManager == null) {
                throw new IllegalArgumentException("Both sslSocketFactory and trustManager must be provided");
            }
            this.httpClient = new OkHttpClient().newBuilder().protocols(Arrays.asList(Protocol.HTTP_1_1)).sslSocketFactory(sslSocketFactory, trustManager).build();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public synchronized Credentials fetch() {
        if (this.credentials != null && !this.credentials.isExpired()) {
            return this.credentials;
        }
        try (okhttp3.Response response = this.httpClient.newCall(this.getRequest()).execute();){
            if (!response.isSuccessful()) {
                throw new ProviderException("STS service failed with HTTP status code " + response.code());
            }
            Credentials credentials = this.credentials = this.parseResponse(response);
            return credentials;
        }
        catch (XmlParserException | IOException e) {
            throw new ProviderException("Unable to parse STS response", e);
        }
    }

    protected static int getValidDurationSeconds(Integer duration) {
        return duration != null && duration > DEFAULT_DURATION_SECONDS ? duration : DEFAULT_DURATION_SECONDS;
    }

    protected HttpUrl.Builder newUrlBuilder(HttpUrl url2, String action, int durationSeconds, String policy, String roleArn, String roleSessionName) {
        HttpUrl.Builder urlBuilder = url2.newBuilder().addQueryParameter("Action", action).addQueryParameter("Version", "2011-06-15");
        if (durationSeconds > 0) {
            urlBuilder.addQueryParameter("DurationSeconds", String.valueOf(durationSeconds));
        }
        if (policy != null) {
            urlBuilder.addQueryParameter("Policy", policy);
        }
        if (roleArn != null) {
            urlBuilder.addQueryParameter("RoleArn", roleArn);
        }
        if (roleSessionName != null) {
            urlBuilder.addQueryParameter("RoleSessionName", roleSessionName);
        }
        return urlBuilder;
    }

    protected Credentials parseResponse(okhttp3.Response response) throws XmlParserException, IOException {
        Response result = Xml.unmarshal(this.getResponseClass(), response.body().charStream());
        return result.getCredentials();
    }

    protected abstract Request getRequest();

    protected abstract Class<? extends Response> getResponseClass();

    public static interface Response {
        public Credentials getCredentials();
    }
}

