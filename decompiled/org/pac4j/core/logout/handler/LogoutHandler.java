/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.logout.handler;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;

public interface LogoutHandler {
    default public void recordSession(WebContext context, SessionStore sessionStore, String key) {
    }

    default public void destroySessionFront(WebContext context, SessionStore sessionStore, String key) {
    }

    default public void destroySessionBack(WebContext context, SessionStore sessionStore, String key) {
    }

    default public void renewSession(String oldSessionId, WebContext context, SessionStore sessionStore) {
    }
}

