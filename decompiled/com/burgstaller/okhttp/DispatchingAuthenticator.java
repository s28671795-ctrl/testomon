/*
 * Decompiled with CFR 0.152.
 */
package com.burgstaller.okhttp;

import com.burgstaller.okhttp.digest.CachingAuthenticator;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import okhttp3.Authenticator;
import okhttp3.Challenge;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class DispatchingAuthenticator
implements CachingAuthenticator {
    private final Map<String, Authenticator> authenticatorRegistry;
    private final Map<String, CachingAuthenticator> cachingRegistry;

    private DispatchingAuthenticator(Map<String, Authenticator> registry) {
        this.authenticatorRegistry = registry;
        this.cachingRegistry = new LinkedHashMap<String, CachingAuthenticator>();
        for (Map.Entry<String, Authenticator> entry : this.authenticatorRegistry.entrySet()) {
            if (!(entry.getValue() instanceof CachingAuthenticator)) continue;
            this.cachingRegistry.put(entry.getKey(), (CachingAuthenticator)entry.getValue());
        }
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        List<Challenge> challenges = response.challenges();
        if (!challenges.isEmpty()) {
            for (Challenge challenge : challenges) {
                String scheme2 = challenge.scheme();
                Authenticator authenticator = null;
                if (scheme2 != null) {
                    authenticator = this.authenticatorRegistry.get(scheme2.toLowerCase(Locale.getDefault()));
                }
                if (authenticator == null) continue;
                return authenticator.authenticate(route, response);
            }
        }
        return null;
    }

    @Override
    public Request authenticateWithState(Route route, Request request) throws IOException {
        for (Map.Entry<String, CachingAuthenticator> authenticatorEntry : this.cachingRegistry.entrySet()) {
            Request authRequest = authenticatorEntry.getValue().authenticateWithState(route, request);
            if (authRequest == null) continue;
            return authRequest;
        }
        return null;
    }

    public static final class Builder {
        Map<String, Authenticator> registry = new LinkedHashMap<String, Authenticator>();

        public Builder with(String scheme2, Authenticator authenticator) {
            this.registry.put(scheme2.toLowerCase(Locale.getDefault()), authenticator);
            return this;
        }

        public DispatchingAuthenticator build() {
            return new DispatchingAuthenticator(this.registry);
        }
    }
}

