/*
 * Decompiled with CFR 0.152.
 */
package com.burgstaller.okhttp;

import com.burgstaller.okhttp.CacheKeyProvider;
import com.burgstaller.okhttp.DefaultCacheKeyProvider;
import com.burgstaller.okhttp.digest.CachingAuthenticator;
import java.io.IOException;
import java.util.Map;
import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.platform.Platform;

public class AuthenticationCacheInterceptor
implements Interceptor {
    private final Map<String, CachingAuthenticator> authCache;
    private final CacheKeyProvider cacheKeyProvider;

    public AuthenticationCacheInterceptor(Map<String, CachingAuthenticator> authCache, CacheKeyProvider cacheKeyProvider) {
        this.authCache = authCache;
        this.cacheKeyProvider = cacheKeyProvider;
    }

    public AuthenticationCacheInterceptor(Map<String, CachingAuthenticator> authCache) {
        this(authCache, new DefaultCacheKeyProvider());
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response;
        int responseCode;
        Route route;
        Request request = chain.request();
        String key = this.cacheKeyProvider.getCachingKey(request);
        CachingAuthenticator authenticator = this.authCache.get(key);
        Request authRequest = null;
        Connection connection = chain.connection();
        Route route2 = route = connection != null ? connection.route() : null;
        if (authenticator != null) {
            authRequest = authenticator.authenticateWithState(route, request);
        }
        if (authRequest == null) {
            authRequest = request;
        }
        int n = responseCode = (response = chain.proceed(authRequest)) != null ? response.code() : 0;
        if (authenticator != null && (responseCode == 401 || responseCode == 407) && this.authCache.remove(key) != null) {
            response.body().close();
            Platform.get().log(4, "Cached authentication expired. Sending a new request.", null);
            response = chain.proceed(request);
        }
        return response;
    }
}

