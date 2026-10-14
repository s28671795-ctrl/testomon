/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.conscrypt.AbstractSessionContext;
import org.conscrypt.NativeSslSession;
import org.conscrypt.SSLClientSessionCache;
import org.conscrypt.SSLParametersImpl;

public final class ClientSessionContext
extends AbstractSessionContext {
    private final Map<HostAndPort, List<NativeSslSession>> sessionsByHostAndPort = new HashMap<HostAndPort, List<NativeSslSession>>();
    private SSLClientSessionCache persistentCache;

    ClientSessionContext() {
        super(10);
    }

    public void setPersistentCache(SSLClientSessionCache persistentCache) {
        this.persistentCache = persistentCache;
    }

    synchronized NativeSslSession getCachedSession(String hostName, int port, SSLParametersImpl sslParameters) {
        if (hostName == null) {
            return null;
        }
        NativeSslSession session = this.getSession(hostName, port);
        if (session == null) {
            return null;
        }
        String protocol = session.getProtocol();
        boolean protocolFound = false;
        for (String enabledProtocol : sslParameters.enabledProtocols) {
            if (!protocol.equals(enabledProtocol)) continue;
            protocolFound = true;
            break;
        }
        if (!protocolFound) {
            return null;
        }
        String cipherSuite = session.getCipherSuite();
        boolean cipherSuiteFound = false;
        for (String enabledCipherSuite : sslParameters.getEnabledCipherSuites()) {
            if (!cipherSuite.equals(enabledCipherSuite)) continue;
            cipherSuiteFound = true;
            break;
        }
        if (!cipherSuiteFound) {
            return null;
        }
        if (session.isSingleUse()) {
            this.removeSession(session);
        }
        return session;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    int size() {
        int size = 0;
        Map<HostAndPort, List<NativeSslSession>> map = this.sessionsByHostAndPort;
        synchronized (map) {
            for (List<NativeSslSession> sessions : this.sessionsByHostAndPort.values()) {
                size += sessions.size();
            }
        }
        return size;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private NativeSslSession getSession(String host2, int port) {
        byte[] data;
        if (host2 == null) {
            return null;
        }
        HostAndPort key = new HostAndPort(host2, port);
        NativeSslSession session = null;
        Map<HostAndPort, List<NativeSslSession>> map = this.sessionsByHostAndPort;
        synchronized (map) {
            List<NativeSslSession> sessions = this.sessionsByHostAndPort.get(key);
            if (sessions != null && sessions.size() > 0) {
                session = sessions.get(0);
            }
        }
        if (session != null && session.isValid()) {
            return session;
        }
        if (this.persistentCache != null && (data = this.persistentCache.getSessionData(host2, port)) != null && (session = NativeSslSession.newInstance(this, data, host2, port)) != null && session.isValid()) {
            this.putSession(key, session);
            return session;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void putSession(HostAndPort key, NativeSslSession session) {
        Map<HostAndPort, List<NativeSslSession>> map = this.sessionsByHostAndPort;
        synchronized (map) {
            List<NativeSslSession> sessions = this.sessionsByHostAndPort.get(key);
            if (sessions == null) {
                sessions = new ArrayList<NativeSslSession>();
                this.sessionsByHostAndPort.put(key, sessions);
            }
            if (sessions.size() > 0 && sessions.get(0).isSingleUse() != session.isSingleUse()) {
                while (!sessions.isEmpty()) {
                    this.removeSession(sessions.get(0));
                }
                this.sessionsByHostAndPort.put(key, sessions);
            }
            sessions.add(session);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void removeSession(HostAndPort key, NativeSslSession session) {
        Map<HostAndPort, List<NativeSslSession>> map = this.sessionsByHostAndPort;
        synchronized (map) {
            List<NativeSslSession> sessions = this.sessionsByHostAndPort.get(key);
            if (sessions != null) {
                sessions.remove(session);
                if (sessions.isEmpty()) {
                    this.sessionsByHostAndPort.remove(key);
                }
            }
        }
    }

    @Override
    void onBeforeAddSession(NativeSslSession session) {
        byte[] data;
        String host2 = session.getPeerHost();
        int port = session.getPeerPort();
        if (host2 == null) {
            return;
        }
        HostAndPort key = new HostAndPort(host2, port);
        this.putSession(key, session);
        if (this.persistentCache != null && !session.isSingleUse() && (data = session.toBytes()) != null) {
            this.persistentCache.putSessionData(session.toSSLSession(), data);
        }
    }

    @Override
    void onBeforeRemoveSession(NativeSslSession session) {
        String host2 = session.getPeerHost();
        if (host2 == null) {
            return;
        }
        int port = session.getPeerPort();
        HostAndPort hostAndPortKey = new HostAndPort(host2, port);
        this.removeSession(hostAndPortKey, session);
    }

    @Override
    NativeSslSession getSessionFromPersistentCache(byte[] sessionId) {
        return null;
    }

    private static final class HostAndPort {
        final String host;
        final int port;

        HostAndPort(String host2, int port) {
            this.host = host2;
            this.port = port;
        }

        public int hashCode() {
            return this.host.hashCode() * 31 + this.port;
        }

        public boolean equals(Object o) {
            if (!(o instanceof HostAndPort)) {
                return false;
            }
            HostAndPort lhs = (HostAndPort)o;
            return this.host.equals(lhs.host) && this.port == lhs.port;
        }
    }
}

