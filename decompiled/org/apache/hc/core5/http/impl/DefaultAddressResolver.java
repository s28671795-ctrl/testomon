/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.core5.http.impl;

import java.net.InetSocketAddress;
import org.apache.hc.core5.function.Resolver;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.URIScheme;

public final class DefaultAddressResolver
implements Resolver<HttpHost, InetSocketAddress> {
    public static final DefaultAddressResolver INSTANCE = new DefaultAddressResolver();

    @Override
    public InetSocketAddress resolve(HttpHost host2) {
        if (host2 == null) {
            return null;
        }
        int port = host2.getPort();
        if (port < 0) {
            String scheme2 = host2.getSchemeName();
            if (URIScheme.HTTP.same(scheme2)) {
                port = 80;
            } else if (URIScheme.HTTPS.same(scheme2)) {
                port = 443;
            }
        }
        return new InetSocketAddress(host2.getHostName(), port);
    }
}

