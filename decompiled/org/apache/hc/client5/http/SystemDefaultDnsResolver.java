/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.client5.http;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.hc.client5.http.DnsResolver;

public class SystemDefaultDnsResolver
implements DnsResolver {
    public static final SystemDefaultDnsResolver INSTANCE = new SystemDefaultDnsResolver();

    @Override
    public InetAddress[] resolve(String host2) throws UnknownHostException {
        return InetAddress.getAllByName(host2);
    }

    @Override
    public String resolveCanonicalHostname(String host2) throws UnknownHostException {
        if (host2 == null) {
            return null;
        }
        InetAddress in = InetAddress.getByName(host2);
        String canonicalServer = in.getCanonicalHostName();
        if (in.getHostAddress().contentEquals(canonicalServer)) {
            return host2;
        }
        return canonicalServer;
    }
}

