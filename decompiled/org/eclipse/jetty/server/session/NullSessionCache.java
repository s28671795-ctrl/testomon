/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.session;

import jakarta.servlet.http.HttpServletRequest;
import java.util.function.Function;
import org.eclipse.jetty.server.session.AbstractSessionCache;
import org.eclipse.jetty.server.session.Session;
import org.eclipse.jetty.server.session.SessionData;
import org.eclipse.jetty.server.session.SessionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NullSessionCache
extends AbstractSessionCache {
    private static final Logger LOG = LoggerFactory.getLogger(NullSessionCache.class);

    public NullSessionCache(SessionHandler handler) {
        super(handler);
        super.setEvictionPolicy(0);
    }

    @Override
    public void shutdown() {
    }

    @Override
    public Session newSession(SessionData data) {
        return new Session(this.getSessionHandler(), data);
    }

    @Override
    public Session newSession(HttpServletRequest request, SessionData data) {
        return new Session(this.getSessionHandler(), request, data);
    }

    @Override
    public Session doGet(String id) {
        return null;
    }

    @Override
    public Session doPutIfAbsent(String id, Session session) {
        return null;
    }

    @Override
    public boolean doReplace(String id, Session oldValue, Session newValue) {
        return true;
    }

    @Override
    public Session doDelete(String id) {
        return null;
    }

    @Override
    public void setEvictionPolicy(int evictionTimeout) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Ignoring eviction setting: {}", (Object)evictionTimeout);
        }
    }

    @Override
    protected Session doComputeIfAbsent(String id, Function<String, Session> mappingFunction) {
        return mappingFunction.apply(id);
    }
}

