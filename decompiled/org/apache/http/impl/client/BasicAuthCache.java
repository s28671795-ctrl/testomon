/*
 * Decompiled with CFR 0.152.
 */
package org.apache.http.impl.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.AuthScheme;
import org.apache.http.client.AuthCache;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.UnsupportedSchemeException;
import org.apache.http.impl.conn.DefaultSchemePortResolver;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.SAFE)
public class BasicAuthCache
implements AuthCache {
    private final Log log = LogFactory.getLog(this.getClass());
    private final Map<HttpHost, byte[]> map = new ConcurrentHashMap<HttpHost, byte[]>();
    private final SchemePortResolver schemePortResolver;

    public BasicAuthCache(SchemePortResolver schemePortResolver) {
        this.schemePortResolver = schemePortResolver != null ? schemePortResolver : DefaultSchemePortResolver.INSTANCE;
    }

    public BasicAuthCache() {
        this(null);
    }

    protected HttpHost getKey(HttpHost host2) {
        if (host2.getPort() <= 0) {
            int port;
            try {
                port = this.schemePortResolver.resolve(host2);
            }
            catch (UnsupportedSchemeException ignore) {
                return host2;
            }
            return new HttpHost(host2.getHostName(), port, host2.getSchemeName());
        }
        return host2;
    }

    @Override
    public void put(HttpHost host2, AuthScheme authScheme) {
        Args.notNull(host2, "HTTP host");
        if (authScheme == null) {
            return;
        }
        if (authScheme instanceof Serializable) {
            try {
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(buf);
                out.writeObject(authScheme);
                out.close();
                this.map.put(this.getKey(host2), buf.toByteArray());
            }
            catch (IOException ex) {
                if (this.log.isWarnEnabled()) {
                    this.log.warn("Unexpected I/O error while serializing auth scheme", ex);
                }
            }
        } else if (this.log.isDebugEnabled()) {
            this.log.debug("Auth scheme " + authScheme.getClass() + " is not serializable");
        }
    }

    @Override
    public AuthScheme get(HttpHost host2) {
        block5: {
            Args.notNull(host2, "HTTP host");
            byte[] bytes = this.map.get(this.getKey(host2));
            if (bytes != null) {
                try {
                    ByteArrayInputStream buf = new ByteArrayInputStream(bytes);
                    ObjectInputStream in = new ObjectInputStream(buf);
                    AuthScheme authScheme = (AuthScheme)in.readObject();
                    in.close();
                    return authScheme;
                }
                catch (IOException ex) {
                    if (this.log.isWarnEnabled()) {
                        this.log.warn("Unexpected I/O error while de-serializing auth scheme", ex);
                    }
                }
                catch (ClassNotFoundException ex) {
                    if (!this.log.isWarnEnabled()) break block5;
                    this.log.warn("Unexpected error while de-serializing auth scheme", ex);
                }
            }
        }
        return null;
    }

    @Override
    public void remove(HttpHost host2) {
        Args.notNull(host2, "HTTP host");
        this.map.remove(this.getKey(host2));
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    public String toString() {
        return this.map.toString();
    }
}

