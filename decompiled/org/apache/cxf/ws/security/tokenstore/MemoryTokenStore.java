/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.tokenstore;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStore;

public class MemoryTokenStore
implements TokenStore {
    public static final long DEFAULT_TTL = 300L;
    public static final long MAX_TTL = 3600L;
    private Map<String, CacheEntry> tokens = new ConcurrentHashMap<String, CacheEntry>();
    private long ttl = 300L;

    @Override
    public void add(SecurityToken token) {
        if (token != null && !StringUtils.isEmpty(token.getId())) {
            this.tokens.put(token.getId(), this.createCacheEntry(token));
        }
    }

    @Override
    public void add(String identifier, SecurityToken token) {
        if (token != null && !StringUtils.isEmpty(identifier)) {
            this.tokens.put(identifier, this.createCacheEntry(token));
        }
    }

    public void setTTL(long newTtl) {
        this.ttl = newTtl;
    }

    @Override
    public void remove(String identifier) {
        if (!StringUtils.isEmpty(identifier) && this.tokens.containsKey(identifier)) {
            this.tokens.remove(identifier);
        }
    }

    @Override
    public Collection<String> getTokenIdentifiers() {
        this.processTokenExpiry();
        return this.tokens.keySet();
    }

    @Override
    public SecurityToken getToken(String id) {
        this.processTokenExpiry();
        CacheEntry cacheEntry = this.tokens.get(id);
        if (cacheEntry != null) {
            return cacheEntry.getSecurityToken();
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void processTokenExpiry() {
        Instant current = Instant.now();
        Map<String, CacheEntry> map = this.tokens;
        synchronized (map) {
            for (Map.Entry<String, CacheEntry> entry : this.tokens.entrySet()) {
                if (!entry.getValue().getExpiry().isBefore(current)) continue;
                this.tokens.remove(entry.getKey());
            }
        }
    }

    private CacheEntry createCacheEntry(SecurityToken token) {
        Instant expires = Instant.now().plusSeconds(this.ttl);
        return new CacheEntry(token, expires);
    }

    private static class CacheEntry {
        private final SecurityToken securityToken;
        private final Instant expires;

        CacheEntry(SecurityToken securityToken, Instant expires) {
            this.securityToken = securityToken;
            this.expires = expires;
        }

        public SecurityToken getSecurityToken() {
            return this.securityToken;
        }

        public Instant getExpiry() {
            return this.expires;
        }
    }
}

