/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jmdns.JmDNS
 */
package org.apache.activemq.transport.discovery.zeroconf;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jmdns.JmDNS;

public final class JmDNSFactory {
    static Map<InetAddress, UsageTracker> registry = new HashMap<InetAddress, UsageTracker>();

    private JmDNSFactory() {
    }

    static synchronized JmDNS create(InetAddress address) throws IOException {
        UsageTracker tracker = registry.get(address);
        if (tracker == null) {
            tracker = new UsageTracker();
            tracker.jmDNS = JmDNS.create((InetAddress)address);
            registry.put(address, tracker);
        }
        tracker.count.incrementAndGet();
        return tracker.jmDNS;
    }

    static synchronized boolean onClose(InetAddress address) {
        UsageTracker tracker = registry.get(address);
        if (tracker != null && tracker.count.decrementAndGet() == 0) {
            registry.remove(address);
            return true;
        }
        return false;
    }

    static class UsageTracker {
        AtomicInteger count = new AtomicInteger(0);
        JmDNS jmDNS;

        UsageTracker() {
        }
    }
}

