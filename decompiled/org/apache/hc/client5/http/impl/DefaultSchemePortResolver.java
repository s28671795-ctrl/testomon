/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.client5.http.impl;

import org.apache.hc.client5.http.SchemePortResolver;
import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.net.NamedEndpoint;
import org.apache.hc.core5.util.Args;

@Contract(threading=ThreadingBehavior.STATELESS)
public class DefaultSchemePortResolver
implements SchemePortResolver {
    public static final DefaultSchemePortResolver INSTANCE = new DefaultSchemePortResolver();

    @Override
    public int resolve(HttpHost host2) {
        Args.notNull(host2, "HTTP host");
        return this.resolve(host2.getSchemeName(), host2);
    }

    @Override
    public int resolve(String scheme2, NamedEndpoint endpoint) {
        Args.notNull(endpoint, "Endpoint");
        int port = endpoint.getPort();
        if (port > 0) {
            return port;
        }
        if (URIScheme.HTTP.same(scheme2)) {
            return 80;
        }
        if (URIScheme.HTTPS.same(scheme2)) {
            return 443;
        }
        return -1;
    }
}

