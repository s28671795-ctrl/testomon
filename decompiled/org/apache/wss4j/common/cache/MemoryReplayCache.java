/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.cache;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.wss4j.common.cache.ReplayCache;

public class MemoryReplayCache
implements ReplayCache {
    public static final long DEFAULT_TTL = 300L;
    public static final long MAX_TTL = 3600L;
    private final SortedMap<Instant, List<String>> cache = new TreeMap<Instant, List<String>>();
    private final Set<String> ids = Collections.synchronizedSet(new HashSet());

    @Override
    public void add(String identifier) {
        this.add(identifier, 300L);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void add(String identifier, long timeToLive) {
        if (identifier == null || "".equals(identifier)) {
            return;
        }
        long ttl = timeToLive;
        if (ttl < 0L || ttl > 3600L) {
            ttl = 300L;
        }
        Instant expires = Instant.now().plusSeconds(ttl);
        SortedMap<Instant, List<String>> sortedMap = this.cache;
        synchronized (sortedMap) {
            ArrayList<String> list = (ArrayList<String>)this.cache.get(expires);
            if (list == null) {
                list = new ArrayList<String>(1);
                this.cache.put(expires, list);
            }
            list.add(identifier);
        }
        this.ids.add(identifier);
    }

    @Override
    public boolean contains(String identifier) {
        this.processTokenExpiry();
        if (identifier != null && !"".equals(identifier)) {
            return this.ids.contains(identifier);
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void processTokenExpiry() {
        Instant current = Instant.now();
        SortedMap<Instant, List<String>> sortedMap = this.cache;
        synchronized (sortedMap) {
            Map.Entry<Instant, List<String>> entry;
            Iterator<Map.Entry<Instant, List<String>>> it = this.cache.entrySet().iterator();
            while (it.hasNext() && (entry = it.next()).getKey().isBefore(current)) {
                for (String id : entry.getValue()) {
                    this.ids.remove(id);
                }
                it.remove();
            }
        }
    }

    @Override
    public synchronized void close() throws IOException {
        this.cache.clear();
        this.ids.clear();
    }
}

