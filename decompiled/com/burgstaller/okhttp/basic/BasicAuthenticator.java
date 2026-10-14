/*
 * Decompiled with CFR 0.152.
 */
package com.burgstaller.okhttp.basic;

import com.burgstaller.okhttp.digest.CachingAuthenticator;
import com.burgstaller.okhttp.digest.Credentials;
import java.io.IOException;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.platform.Platform;

public class BasicAuthenticator
implements CachingAuthenticator {
    private final Credentials credentials;
    private boolean proxy;

    public BasicAuthenticator(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        Request request = response.request();
        this.proxy = response.code() == 407;
        return this.authFromRequest(request);
    }

    private Request authFromRequest(Request request) {
        String header = this.proxy ? "Proxy-Authorization" : "Authorization";
        String authorizationHeader = request.header(header);
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic")) {
            Platform.get().log(5, "previous basic authentication failed, returning null", null);
            return null;
        }
        String authValue = okhttp3.Credentials.basic(this.credentials.getUserName(), this.credentials.getPassword());
        return request.newBuilder().header(header, authValue).build();
    }

    @Override
    public Request authenticateWithState(Route route, Request request) throws IOException {
        return this.authFromRequest(request);
    }
}

