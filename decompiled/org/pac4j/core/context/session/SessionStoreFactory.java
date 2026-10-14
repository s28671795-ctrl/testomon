/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.context.session;

import org.pac4j.core.context.session.SessionStore;

@FunctionalInterface
public interface SessionStoreFactory {
    public SessionStore newSessionStore(Object ... var1);
}

