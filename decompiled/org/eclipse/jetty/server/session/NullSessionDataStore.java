/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.session;

import java.util.Collections;
import java.util.Set;
import org.eclipse.jetty.server.session.AbstractSessionDataStore;
import org.eclipse.jetty.server.session.SessionData;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;

@ManagedObject
public class NullSessionDataStore
extends AbstractSessionDataStore {
    @Override
    public SessionData doLoad(String id) throws Exception {
        return null;
    }

    @Override
    public SessionData newSessionData(String id, long created, long accessed, long lastAccessed, long maxInactiveMs) {
        return new SessionData(id, this._context.getCanonicalContextPath(), this._context.getVhost(), created, accessed, lastAccessed, maxInactiveMs);
    }

    @Override
    public boolean delete(String id) throws Exception {
        return true;
    }

    @Override
    public void doStore(String id, SessionData data, long lastSaveTime) throws Exception {
    }

    @Override
    public Set<String> doCheckExpired(Set<String> candidates, long time) {
        return candidates;
    }

    @Override
    public Set<String> doGetExpired(long timeLimit) {
        return Collections.emptySet();
    }

    @Override
    @ManagedAttribute(value="does this store serialize sessions", readonly=true)
    public boolean isPassivating() {
        return false;
    }

    @Override
    public boolean doExists(String id) {
        return false;
    }

    @Override
    public void doCleanOrphans(long timeLimit) {
    }
}

