/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import org.conscrypt.ByteArray;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeSslSession;

abstract class AbstractSessionContext
implements SSLSessionContext {
    private static final int DEFAULT_SESSION_TIMEOUT_SECONDS = 28800;
    private volatile int maximumSize;
    private volatile int timeout = 28800;
    final long sslCtxNativePointer = NativeCrypto.SSL_CTX_new();
    private final Map<ByteArray, NativeSslSession> sessions = new LinkedHashMap<ByteArray, NativeSslSession>(){

        @Override
        protected boolean removeEldestEntry(Map.Entry<ByteArray, NativeSslSession> eldest) {
            if (AbstractSessionContext.this.maximumSize > 0 && this.size() > AbstractSessionContext.this.maximumSize) {
                AbstractSessionContext.this.onBeforeRemoveSession(eldest.getValue());
                return true;
            }
            return false;
        }
    };

    AbstractSessionContext(int maximumSize) {
        this.maximumSize = maximumSize;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final Enumeration<byte[]> getIds() {
        Iterator<NativeSslSession> iter;
        Map<ByteArray, NativeSslSession> map = this.sessions;
        synchronized (map) {
            iter = Arrays.asList(this.sessions.values().toArray(new NativeSslSession[this.sessions.size()])).iterator();
        }
        return new Enumeration<byte[]>(){
            private NativeSslSession next;

            @Override
            public boolean hasMoreElements() {
                if (this.next != null) {
                    return true;
                }
                while (iter.hasNext()) {
                    NativeSslSession session = (NativeSslSession)iter.next();
                    if (!session.isValid()) continue;
                    this.next = session;
                    return true;
                }
                this.next = null;
                return false;
            }

            @Override
            public byte[] nextElement() {
                if (this.hasMoreElements()) {
                    byte[] id = this.next.getId();
                    this.next = null;
                    return id;
                }
                throw new NoSuchElementException();
            }
        };
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final SSLSession getSession(byte[] sessionId) {
        NativeSslSession session;
        if (sessionId == null) {
            throw new NullPointerException("sessionId");
        }
        ByteArray key = new ByteArray(sessionId);
        Map<ByteArray, NativeSslSession> map = this.sessions;
        synchronized (map) {
            session = this.sessions.get(key);
        }
        if (session != null && session.isValid()) {
            return session.toSSLSession();
        }
        return null;
    }

    @Override
    public final int getSessionCacheSize() {
        return this.maximumSize;
    }

    @Override
    public final int getSessionTimeout() {
        return this.timeout;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void setSessionTimeout(int seconds) throws IllegalArgumentException {
        if (seconds < 0) {
            throw new IllegalArgumentException("seconds < 0");
        }
        Map<ByteArray, NativeSslSession> map = this.sessions;
        synchronized (map) {
            this.timeout = seconds;
            if (seconds > 0) {
                NativeCrypto.SSL_CTX_set_timeout(this.sslCtxNativePointer, this, seconds);
            } else {
                NativeCrypto.SSL_CTX_set_timeout(this.sslCtxNativePointer, this, Integer.MAX_VALUE);
            }
            Iterator<NativeSslSession> i = this.sessions.values().iterator();
            while (i.hasNext()) {
                NativeSslSession session = i.next();
                if (session.isValid()) continue;
                this.onBeforeRemoveSession(session);
                i.remove();
            }
        }
    }

    @Override
    public final void setSessionCacheSize(int size) throws IllegalArgumentException {
        if (size < 0) {
            throw new IllegalArgumentException("size < 0");
        }
        int oldMaximum = this.maximumSize;
        this.maximumSize = size;
        if (size < oldMaximum) {
            this.trimToSize();
        }
    }

    protected void finalize() throws Throwable {
        try {
            NativeCrypto.SSL_CTX_free(this.sslCtxNativePointer, this);
        }
        finally {
            super.finalize();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final void cacheSession(NativeSslSession session) {
        byte[] id = session.getId();
        if (id == null || id.length == 0) {
            return;
        }
        Map<ByteArray, NativeSslSession> map = this.sessions;
        synchronized (map) {
            ByteArray key = new ByteArray(id);
            if (this.sessions.containsKey(key)) {
                this.removeSession(this.sessions.get(key));
            }
            this.onBeforeAddSession(session);
            this.sessions.put(key, session);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final void removeSession(NativeSslSession session) {
        byte[] id = session.getId();
        if (id == null || id.length == 0) {
            return;
        }
        this.onBeforeRemoveSession(session);
        ByteArray key = new ByteArray(id);
        Map<ByteArray, NativeSslSession> map = this.sessions;
        synchronized (map) {
            this.sessions.remove(key);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final NativeSslSession getSessionFromCache(byte[] sessionId) {
        NativeSslSession session;
        if (sessionId == null) {
            return null;
        }
        Map<ByteArray, NativeSslSession> map = this.sessions;
        synchronized (map) {
            session = this.sessions.get(new ByteArray(sessionId));
        }
        if (session != null && session.isValid()) {
            if (session.isSingleUse()) {
                this.removeSession(session);
            }
            return session;
        }
        return this.getSessionFromPersistentCache(sessionId);
    }

    abstract void onBeforeAddSession(NativeSslSession var1);

    abstract void onBeforeRemoveSession(NativeSslSession var1);

    abstract NativeSslSession getSessionFromPersistentCache(byte[] var1);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void trimToSize() {
        Map<ByteArray, NativeSslSession> map = this.sessions;
        synchronized (map) {
            int size = this.sessions.size();
            if (size > this.maximumSize) {
                int removals = size - this.maximumSize;
                Iterator<NativeSslSession> i = this.sessions.values().iterator();
                while (removals-- > 0) {
                    NativeSslSession session = i.next();
                    this.onBeforeRemoveSession(session);
                    i.remove();
                }
            }
        }
    }
}

