/*
 * Decompiled with CFR 0.152.
 */
package io.minio.credentials;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.minio.credentials.Credentials;
import io.minio.credentials.EnvironmentProvider;
import io.minio.credentials.Jwt;
import io.minio.credentials.WebIdentityProvider;
import io.minio.messages.ResponseDate;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.ProviderException;
import java.util.Arrays;
import java.util.Objects;
import javax.annotation.Nullable;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IamAwsProvider
extends EnvironmentProvider {
    private final HttpUrl customEndpoint;
    private final OkHttpClient httpClient;
    private final ObjectMapper mapper;
    private Credentials credentials;

    public IamAwsProvider(@Nullable String customEndpoint, @Nullable OkHttpClient customHttpClient) {
        this.customEndpoint = customEndpoint != null ? Objects.requireNonNull(HttpUrl.parse(customEndpoint), "Invalid custom endpoint") : null;
        this.httpClient = customHttpClient != null ? customHttpClient : new OkHttpClient().newBuilder().protocols(Arrays.asList(Protocol.HTTP_1_1)).build();
        this.mapper = ((JsonMapper.Builder)((JsonMapper.Builder)JsonMapper.builder().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)).configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)).build();
    }

    private void checkLoopbackHost(HttpUrl url2) {
        try {
            for (InetAddress addr : InetAddress.getAllByName(url2.host())) {
                if (addr.isLoopbackAddress()) continue;
                throw new ProviderException(url2.host() + " is not loopback only host");
            }
        }
        catch (UnknownHostException e) {
            throw new ProviderException("Host in " + url2 + " is not loopback address");
        }
    }

    private Credentials fetchCredentials(String tokenFile) {
        HttpUrl url2 = this.customEndpoint;
        if (url2 == null) {
            String region = this.getProperty("AWS_REGION");
            url2 = HttpUrl.parse(region == null ? "https://sts.amazonaws.com" : "https://sts." + region + ".amazonaws.com");
        }
        WebIdentityProvider provider = new WebIdentityProvider(() -> {
            try {
                byte[] data = Files.readAllBytes(Paths.get(tokenFile, new String[0]));
                return new Jwt(new String(data, StandardCharsets.UTF_8), 0);
            }
            catch (IOException e) {
                throw new ProviderException("Error in reading file " + tokenFile, e);
            }
        }, url2.toString(), null, null, this.getProperty("AWS_ROLE_ARN"), this.getProperty("AWS_ROLE_SESSION_NAME"), this.httpClient);
        this.credentials = provider.fetch();
        return this.credentials;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Credentials fetchCredentials(HttpUrl url2, String tokenHeader, String token) {
        Request.Builder builder = new Request.Builder().url(url2).method("GET", null);
        if (token != null && !token.isEmpty()) {
            builder.header(tokenHeader, token);
        }
        try (Response response = this.httpClient.newCall(builder.build()).execute();){
            if (!response.isSuccessful()) {
                throw new ProviderException(url2 + " failed with HTTP status code " + response.code());
            }
            EcsCredentials creds = this.mapper.readValue(response.body().charStream(), EcsCredentials.class);
            if (creds.code() != null && !creds.code().equals("Success")) {
                throw new ProviderException(url2 + " failed with code " + creds.code() + " and message " + creds.message());
            }
            Credentials credentials = creds.toCredentials();
            return credentials;
        }
        catch (IOException e) {
            throw new ProviderException("Unable to parse response", e);
        }
    }

    private String fetchImdsToken() {
        HttpUrl url2 = this.customEndpoint;
        url2 = url2 == null ? HttpUrl.parse("http://169.254.169.254/latest/api/token") : new HttpUrl.Builder().scheme(url2.scheme()).host(url2.host()).port(url2.port()).addPathSegments("latest/api/token").build();
        String token = "";
        Request request = new Request.Builder().url(url2).method("PUT", RequestBody.create(new byte[0], null)).header("X-aws-ec2-metadata-token-ttl-seconds", "21600").build();
        try (Response response = this.httpClient.newCall(request).execute();){
            if (response.isSuccessful()) {
                token = response.body().string();
            }
        }
        catch (IOException e) {
            token = "";
        }
        return token;
    }

    private String getIamRoleName(HttpUrl url2, String token) {
        String[] roleNames = null;
        Request.Builder builder = new Request.Builder().url(url2).method("GET", null);
        if (token != null && !token.isEmpty()) {
            builder.header("X-aws-ec2-metadata-token", token);
        }
        try (Response response = this.httpClient.newCall(builder.build()).execute();){
            if (!response.isSuccessful()) {
                throw new ProviderException(url2 + " failed with HTTP status code " + response.code());
            }
            roleNames = response.body().string().split("\\R");
        }
        catch (IOException e) {
            throw new ProviderException("Unable to parse response", e);
        }
        if (roleNames.length == 0) {
            throw new ProviderException("No IAM roles attached to EC2 service " + url2);
        }
        return roleNames[0];
    }

    private HttpUrl getIamRoleNamedUrl(String token) {
        HttpUrl url2 = this.customEndpoint;
        url2 = url2 == null ? HttpUrl.parse("http://169.254.169.254/latest/meta-data/iam/security-credentials/") : new HttpUrl.Builder().scheme(url2.scheme()).host(url2.host()).port(url2.port()).addPathSegments("latest/meta-data/iam/security-credentials/").build();
        String roleName = this.getIamRoleName(url2, token);
        return url2.newBuilder().addPathSegment(roleName).build();
    }

    @Override
    public synchronized Credentials fetch() {
        if (this.credentials != null && !this.credentials.isExpired()) {
            return this.credentials;
        }
        HttpUrl url2 = this.customEndpoint;
        String tokenFile = this.getProperty("AWS_WEB_IDENTITY_TOKEN_FILE");
        if (tokenFile != null) {
            this.credentials = this.fetchCredentials(tokenFile);
            return this.credentials;
        }
        String tokenHeader = "Authorization";
        String token = this.getProperty("AWS_CONTAINER_AUTHORIZATION_TOKEN");
        if (this.getProperty("AWS_CONTAINER_CREDENTIALS_RELATIVE_URI") != null) {
            if (url2 == null) {
                url2 = new HttpUrl.Builder().scheme("http").host("169.254.170.2").addPathSegments(this.getProperty("AWS_CONTAINER_CREDENTIALS_RELATIVE_URI")).build();
            }
        } else if (this.getProperty("AWS_CONTAINER_CREDENTIALS_FULL_URI") != null) {
            if (url2 == null) {
                url2 = HttpUrl.parse(this.getProperty("AWS_CONTAINER_CREDENTIALS_FULL_URI"));
            }
            this.checkLoopbackHost(url2);
        } else {
            token = this.fetchImdsToken();
            tokenHeader = "X-aws-ec2-metadata-token";
            url2 = this.getIamRoleNamedUrl(token);
        }
        this.credentials = this.fetchCredentials(url2, tokenHeader, token);
        return this.credentials;
    }

    public static class EcsCredentials {
        @JsonProperty(value="AccessKeyID")
        private String accessKey;
        @JsonProperty(value="SecretAccessKey")
        private String secretKey;
        @JsonProperty(value="Token")
        private String sessionToken;
        @JsonProperty(value="Expiration")
        private ResponseDate expiration;
        @JsonProperty(value="Code")
        private String code;
        @JsonProperty(value="Message")
        private String message;

        public String code() {
            return this.code;
        }

        public String message() {
            return this.message;
        }

        public Credentials toCredentials() {
            return new Credentials(this.accessKey, this.secretKey, this.sessionToken, this.expiration);
        }
    }
}

