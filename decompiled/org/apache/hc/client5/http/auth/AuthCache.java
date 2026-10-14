/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.client5.http.auth;

import org.apache.hc.client5.http.auth.AuthScheme;
import org.apache.hc.core5.http.HttpHost;

public interface AuthCache {
    public void put(HttpHost var1, AuthScheme var2);

    public AuthScheme get(HttpHost var1);

    public void remove(HttpHost var1);

    public void clear();

    default public void put(HttpHost host2, String pathPrefix, AuthScheme authScheme) {
        this.put(host2, authScheme);
    }

    default public AuthScheme get(HttpHost host2, String pathPrefix) {
        return this.get(host2);
    }

    default public void remove(HttpHost host2, String pathPrefix) {
        this.remove(host2);
    }
}

