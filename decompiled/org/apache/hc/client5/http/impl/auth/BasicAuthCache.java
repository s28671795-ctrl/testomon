/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.client5.http.impl.auth;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.hc.client5.http.SchemePortResolver;
import org.apache.hc.client5.http.auth.AuthCache;
import org.apache.hc.client5.http.auth.AuthScheme;
import org.apache.hc.client5.http.impl.DefaultSchemePortResolver;
import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.net.NamedEndpoint;
import org.apache.hc.core5.util.Args;
import org.apache.hc.core5.util.LangUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Contract(threading=ThreadingBehavior.SAFE_CONDITIONAL)
public class BasicAuthCache
implements AuthCache {
    private static final Logger LOG = LoggerFactory.getLogger(BasicAuthCache.class);
    private final Map<Key, byte[]> map = new ConcurrentHashMap<Key, byte[]>();
    private final SchemePortResolver schemePortResolver;

    public BasicAuthCache(SchemePortResolver schemePortResolver) {
        this.schemePortResolver = schemePortResolver != null ? schemePortResolver : DefaultSchemePortResolver.INSTANCE;
    }

    public BasicAuthCache() {
        this(null);
    }

    private Key key(String scheme2, NamedEndpoint authority, String pathPrefix) {
        return new Key(scheme2, authority.getHostName(), this.schemePortResolver.resolve(scheme2, authority), pathPrefix);
    }

    @Override
    public void put(HttpHost host2, AuthScheme authScheme) {
        this.put(host2, null, authScheme);
    }

    @Override
    public AuthScheme get(HttpHost host2) {
        return this.get(host2, null);
    }

    @Override
    public void remove(HttpHost host2) {
        this.remove(host2, null);
    }

    @Override
    public void put(HttpHost host2, String pathPrefix, AuthScheme authScheme) {
        block19: {
            Args.notNull(host2, "HTTP host");
            if (authScheme == null) {
                return;
            }
            if (authScheme instanceof Serializable) {
                try {
                    ByteArrayOutputStream buf = new ByteArrayOutputStream();
                    try (ObjectOutputStream out = new ObjectOutputStream(buf);){
                        out.writeObject(authScheme);
                    }
                    this.map.put(this.key(host2.getSchemeName(), host2, pathPrefix), buf.toByteArray());
                }
                catch (IOException ex) {
                    if (LOG.isWarnEnabled()) {
                        LOG.warn("Unexpected I/O error while serializing auth scheme", ex);
                    }
                    break block19;
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Auth scheme {} is not serializable", (Object)authScheme.getClass());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AuthScheme get(HttpHost host2, String pathPrefix) {
        Args.notNull(host2, "HTTP host");
        byte[] bytes = this.map.get(this.key(host2.getSchemeName(), host2, pathPrefix));
        if (bytes == null) return null;
        try {
            ByteArrayInputStream buf = new ByteArrayInputStream(bytes);
            try (ObjectInputStream in = new ObjectInputStream(buf);){
                AuthScheme authScheme = (AuthScheme)in.readObject();
                return authScheme;
            }
        }
        catch (IOException ex) {
            if (!LOG.isWarnEnabled()) return null;
            LOG.warn("Unexpected I/O error while de-serializing auth scheme", ex);
            return null;
        }
        catch (ClassNotFoundException ex) {
            if (!LOG.isWarnEnabled()) return null;
            LOG.warn("Unexpected error while de-serializing auth scheme", ex);
        }
        return null;
    }

    @Override
    public void remove(HttpHost host2, String pathPrefix) {
        Args.notNull(host2, "HTTP host");
        this.map.remove(this.key(host2.getSchemeName(), host2, pathPrefix));
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    public String toString() {
        return this.map.toString();
    }

    static class Key {
        final String scheme;
        final String host;
        final int port;
        final String pathPrefix;

        Key(String scheme2, String host2, int port, String pathPrefix) {
            Args.notBlank(scheme2, "Scheme");
            Args.notBlank(host2, "Scheme");
            this.scheme = scheme2.toLowerCase(Locale.ROOT);
            this.host = host2.toLowerCase(Locale.ROOT);
            this.port = port;
            this.pathPrefix = pathPrefix;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Key) {
                Key that = (Key)obj;
                return this.scheme.equals(that.scheme) && this.host.equals(that.host) && this.port == that.port && Objects.equals(this.pathPrefix, that.pathPrefix);
            }
            return false;
        }

        public int hashCode() {
            int hash = 17;
            hash = LangUtils.hashCode(hash, this.scheme);
            hash = LangUtils.hashCode(hash, this.host);
            hash = LangUtils.hashCode(hash, this.port);
            hash = LangUtils.hashCode(hash, this.pathPrefix);
            return hash;
        }

        public String toString() {
            StringBuilder buf = new StringBuilder();
            buf.append(this.scheme).append("://").append(this.host);
            if (this.port >= 0) {
                buf.append(":").append(this.port);
            }
            if (this.pathPrefix != null) {
                if (!this.pathPrefix.startsWith("/")) {
                    buf.append("/");
                }
                buf.append(this.pathPrefix);
            }
            return buf.toString();
        }
    }
}

