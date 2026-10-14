/*
 * Decompiled with CFR 0.152.
 */
package com.burgstaller.okhttp;

import com.burgstaller.okhttp.CacheKeyProvider;
import com.burgstaller.okhttp.DefaultCacheKeyProvider;
import com.burgstaller.okhttp.digest.CachingAuthenticator;
import java.io.IOException;
import java.util.Map;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class CachingAuthenticatorDecorator
implements Authenticator {
    private final Authenticator innerAuthenticator;
    private final Map<String, CachingAuthenticator> authCache;
    private final CacheKeyProvider cacheKeyProvider;

    public CachingAuthenticatorDecorator(Authenticator innerAuthenticator, Map<String, CachingAuthenticator> authCache, CacheKeyProvider cacheKeyProvider) {
        this.innerAuthenticator = innerAuthenticator;
        this.authCache = authCache;
        this.cacheKeyProvider = cacheKeyProvider;
    }

    public CachingAuthenticatorDecorator(Authenticator innerAuthenticator, Map<String, CachingAuthenticator> authCache) {
        this(innerAuthenticator, authCache, new DefaultCacheKeyProvider());
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        String authorizationValue;
        Request authenticated = this.innerAuthenticator.authenticate(route, response);
        if (authenticated != null && (authorizationValue = authenticated.header("Authorization")) != null && this.innerAuthenticator instanceof CachingAuthenticator) {
            String key = this.cacheKeyProvider.getCachingKey(authenticated);
            this.authCache.put(key, (CachingAuthenticator)this.innerAuthenticator);
        }
        return authenticated;
    }
}

