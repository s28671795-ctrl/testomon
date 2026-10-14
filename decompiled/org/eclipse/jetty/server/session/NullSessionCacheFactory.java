/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.session;

import org.eclipse.jetty.server.session.AbstractSessionCacheFactory;
import org.eclipse.jetty.server.session.NullSessionCache;
import org.eclipse.jetty.server.session.SessionCache;
import org.eclipse.jetty.server.session.SessionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NullSessionCacheFactory
extends AbstractSessionCacheFactory {
    private static final Logger LOG = LoggerFactory.getLogger(NullSessionCacheFactory.class);

    @Override
    public int getEvictionPolicy() {
        return 0;
    }

    @Override
    public void setEvictionPolicy(int evictionPolicy) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Ignoring eviction policy setting for NullSessionCaches");
        }
    }

    @Override
    public boolean isSaveOnInactiveEvict() {
        return false;
    }

    @Override
    public void setSaveOnInactiveEvict(boolean saveOnInactiveEvict) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Ignoring eviction policy setting for NullSessionCaches");
        }
    }

    @Override
    public boolean isInvalidateOnShutdown() {
        return false;
    }

    @Override
    public void setInvalidateOnShutdown(boolean invalidateOnShutdown) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Ignoring invalidateOnShutdown setting for NullSessionCaches");
        }
    }

    @Override
    public SessionCache newSessionCache(SessionHandler handler) {
        return new NullSessionCache(handler);
    }
}

