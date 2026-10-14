/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.sessions;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.Session;
import org.eclipse.jetty.server.session.SessionHandler;

public class SessionCacheImpl
extends DefaultSessionCache {
    private final ConcurrentMap<String, Session> sessions;

    public SessionCacheImpl(SessionHandler manager, ConcurrentMap<String, Session> sessions) {
        super(manager, sessions);
        this.sessions = sessions;
    }

    public List<Session> getSessions() {
        return List.copyOf(this.sessions.values());
    }
}

