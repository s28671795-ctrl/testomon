/*
 * Decompiled with CFR 0.152.
 */
package io.minio.credentials;

import io.minio.credentials.AssumeRoleBaseProvider;
import io.minio.credentials.Credentials;
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

public class LdapIdentityProvider
extends AssumeRoleBaseProvider {
    private static final RequestBody EMPTY_BODY = RequestBody.create(new byte[0], MediaType.parse("application/octet-stream"));
    private final Request request;

    public LdapIdentityProvider(@Nonnull String stsEndpoint, @Nonnull String ldapUsername, @Nonnull String ldapPassword, @Nullable Integer durationSeconds, @Nullable String policy, @Nullable OkHttpClient customHttpClient) {
        super(customHttpClient);
        stsEndpoint = Objects.requireNonNull(stsEndpoint, "STS endpoint cannot be empty");
        HttpUrl url2 = Objects.requireNonNull(HttpUrl.parse(stsEndpoint), "Invalid STS endpoint");
        if (ldapUsername == null || ldapUsername.isEmpty()) {
            throw new IllegalArgumentException("LDAP username must be provided");
        }
        Objects.requireNonNull(ldapPassword, "LDAP password must not be null");
        HttpUrl.Builder urlBuilder = this.newUrlBuilder(url2, "AssumeRoleWithLDAPIdentity", LdapIdentityProvider.getValidDurationSeconds(durationSeconds), policy, null, null);
        url2 = urlBuilder.addQueryParameter("LDAPUsername", ldapUsername).addQueryParameter("LDAPPassword", ldapPassword).build();
        this.request = new Request.Builder().url(url2).method("POST", EMPTY_BODY).build();
    }

    public LdapIdentityProvider(@Nonnull String stsEndpoint, @Nonnull String ldapUsername, @Nonnull String ldapPassword, @Nullable OkHttpClient customHttpClient) {
        this(stsEndpoint, ldapUsername, ldapPassword, null, null, customHttpClient);
    }

    @Override
    protected Request getRequest() {
        return this.request;
    }

    @Override
    protected Class<? extends AssumeRoleBaseProvider.Response> getResponseClass() {
        return LdapIdentityResponse.class;
    }

    @Root(name="AssumeRoleWithLDAPIdentityResponse", strict=false)
    @Namespace(reference="https://sts.amazonaws.com/doc/2011-06-15/")
    public static class LdapIdentityResponse
    implements AssumeRoleBaseProvider.Response {
        @Path(value="AssumeRoleWithLDAPIdentityResult")
        @Element(name="Credentials")
        private Credentials credentials;

        @Override
        public Credentials getCredentials() {
            return this.credentials;
        }
    }
}

