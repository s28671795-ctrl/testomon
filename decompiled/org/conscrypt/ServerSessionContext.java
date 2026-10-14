/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import org.conscrypt.AbstractSessionContext;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeSslSession;
import org.conscrypt.SSLServerSessionCache;

public final class ServerSessionContext
extends AbstractSessionContext {
    private SSLServerSessionCache persistentCache;

    ServerSessionContext() {
        super(100);
        NativeCrypto.SSL_CTX_set_session_id_context(this.sslCtxNativePointer, this, new byte[]{32});
    }

    public void setPersistentCache(SSLServerSessionCache persistentCache) {
        this.persistentCache = persistentCache;
    }

    @Override
    NativeSslSession getSessionFromPersistentCache(byte[] sessionId) {
        NativeSslSession session;
        byte[] data;
        if (this.persistentCache != null && (data = this.persistentCache.getSessionData(sessionId)) != null && (session = NativeSslSession.newInstance(this, data, null, -1)) != null && session.isValid()) {
            this.cacheSession(session);
            return session;
        }
        return null;
    }

    @Override
    void onBeforeAddSession(NativeSslSession session) {
        byte[] data;
        if (this.persistentCache != null && (data = session.toBytes()) != null) {
            this.persistentCache.putSessionData(session.toSSLSession(), data);
        }
    }

    @Override
    void onBeforeRemoveSession(NativeSslSession session) {
    }
}

