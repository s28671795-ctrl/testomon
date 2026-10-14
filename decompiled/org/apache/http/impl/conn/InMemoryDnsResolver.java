/*
 * Decompiled with CFR 0.152.
 */
package org.apache.http.impl.conn;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.DnsResolver;
import org.apache.http.util.Args;

public class InMemoryDnsResolver
implements DnsResolver {
    private final Log log = LogFactory.getLog(InMemoryDnsResolver.class);
    private final Map<String, InetAddress[]> dnsMap = new ConcurrentHashMap<String, InetAddress[]>();

    public void add(String host2, InetAddress ... ips) {
        Args.notNull(host2, "Host name");
        Args.notNull(ips, "Array of IP addresses");
        this.dnsMap.put(host2, ips);
    }

    @Override
    public InetAddress[] resolve(String host2) throws UnknownHostException {
        Object[] resolvedAddresses = this.dnsMap.get(host2);
        if (this.log.isInfoEnabled()) {
            this.log.info("Resolving " + host2 + " to " + Arrays.deepToString(resolvedAddresses));
        }
        if (resolvedAddresses == null) {
            throw new UnknownHostException(host2 + " cannot be resolved");
        }
        return resolvedAddresses;
    }
}

