/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.client5.http.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.hc.client5.http.DnsResolver;
import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.util.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Contract(threading=ThreadingBehavior.STATELESS)
public class InMemoryDnsResolver
implements DnsResolver {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryDnsResolver.class);
    private final Map<String, InetAddress[]> dnsMap = new ConcurrentHashMap<String, InetAddress[]>();

    public void add(String host2, InetAddress ... ips) {
        Args.notNull(host2, "Host name");
        Args.notNull(ips, "Array of IP addresses");
        this.dnsMap.put(host2, ips);
    }

    @Override
    public InetAddress[] resolve(String host2) throws UnknownHostException {
        Object[] resolvedAddresses = this.dnsMap.get(host2);
        if (LOG.isInfoEnabled()) {
            LOG.info("Resolving {} to {}", (Object)host2, (Object)Arrays.deepToString(resolvedAddresses));
        }
        if (resolvedAddresses == null) {
            throw new UnknownHostException(host2 + " cannot be resolved");
        }
        return resolvedAddresses;
    }

    @Override
    public String resolveCanonicalHostname(String host2) throws UnknownHostException {
        InetAddress[] resolvedAddresses = this.resolve(host2);
        if (resolvedAddresses.length > 0) {
            return resolvedAddresses[0].getCanonicalHostName();
        }
        return host2;
    }
}

