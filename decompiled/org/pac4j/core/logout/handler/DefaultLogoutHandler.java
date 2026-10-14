/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.logout.handler;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.logout.handler.LogoutHandler;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.factory.ProfileManagerFactoryAware;
import org.pac4j.core.store.GuavaStore;
import org.pac4j.core.store.Store;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLogoutHandler
extends ProfileManagerFactoryAware
implements LogoutHandler {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Store<String, Object> store = new GuavaStore<String, Object>(10000, 30, TimeUnit.MINUTES);
    private boolean destroySession;

    public DefaultLogoutHandler() {
    }

    public DefaultLogoutHandler(Store<String, Object> store) {
        this.store = store;
    }

    @Override
    public void recordSession(WebContext context, SessionStore sessionStore, String key) {
        if (sessionStore == null) {
            this.logger.error("No session store available for this web context");
        } else {
            Optional<String> optSessionId = sessionStore.getSessionId(context, true);
            if (optSessionId.isEmpty()) {
                this.logger.error("No session identifier retrieved although the session creation has been requested");
            } else {
                String sessionId = optSessionId.get();
                Optional<Object> optTrackableSession = sessionStore.getTrackableSession(context);
                if (optTrackableSession.isPresent()) {
                    Object trackableSession = optTrackableSession.get();
                    this.logger.debug("key: {} -> trackableSession: {}", (Object)key, trackableSession);
                    this.logger.debug("sessionId: {}", (Object)sessionId);
                    this.store.set(key, trackableSession);
                    this.store.set(sessionId, key);
                } else {
                    this.logger.debug("No trackable session for the current session store: {}", (Object)sessionStore);
                }
            }
        }
    }

    @Override
    public void destroySessionFront(WebContext context, SessionStore sessionStore, String key) {
        if (sessionStore == null) {
            this.logger.error("No session store available for this web context");
        } else {
            Optional<String> optCurrentSessionId = sessionStore.getSessionId(context, false);
            if (optCurrentSessionId.isPresent()) {
                this.store.remove(key);
                String currentSessionId = optCurrentSessionId.get();
                this.logger.debug("currentSessionId: {}", (Object)currentSessionId);
                String sessionToKey = this.store.get(currentSessionId).orElse(null);
                this.logger.debug("-> key: {}", (Object)key);
                this.store.remove(currentSessionId);
                if (CommonHelper.areEquals(key, sessionToKey)) {
                    this.destroy(context, sessionStore, "front");
                } else {
                    this.logger.error("The user profiles (and session) can not be destroyed for the front channel logout because the provided key is not the same as the one linked to the current session");
                }
            } else {
                this.logger.warn("no session for front channel logout => trying back channel logout");
                this.destroySessionBack(context, sessionStore, key);
            }
        }
    }

    protected void destroy(WebContext context, SessionStore sessionStore, String channel) {
        ProfileManager manager = this.getProfileManager(context, sessionStore);
        manager.removeProfiles();
        this.logger.debug("{} channel logout call: destroy the user profiles", (Object)channel);
        if (this.destroySession) {
            this.logger.debug("destroy the whole session");
            boolean invalidated = sessionStore.destroySession(context);
            if (!invalidated) {
                this.logger.error("The session has not been invalidated");
            }
        }
    }

    @Override
    public void destroySessionBack(WebContext context, SessionStore sessionStore, String key) {
        Optional<Object> optTrackableSession = this.store.get(key);
        this.logger.debug("key: {} -> trackableSession: {}", (Object)key, (Object)optTrackableSession);
        if (!optTrackableSession.isPresent()) {
            this.logger.error("No trackable session found for back channel logout. Either the session store does not support to track session or it has expired from the store and the store settings must be updated (expired data)");
        } else {
            this.store.remove(key);
            if (sessionStore == null) {
                this.logger.error("No session store available for this web context");
            } else {
                Optional<SessionStore> optNewSessionStore = sessionStore.buildFromTrackableSession(context, optTrackableSession.get());
                if (optNewSessionStore.isPresent()) {
                    SessionStore newSessionStore = optNewSessionStore.get();
                    this.logger.debug("newSesionStore: {}", (Object)newSessionStore);
                    String sessionId = newSessionStore.getSessionId(context, true).get();
                    this.logger.debug("remove sessionId: {}", (Object)sessionId);
                    this.store.remove(sessionId);
                    this.destroy(context, newSessionStore, "back");
                } else {
                    this.logger.error("The session store should be able to build a new session store from the tracked session");
                }
            }
        }
    }

    @Override
    public void renewSession(String oldSessionId, WebContext context, SessionStore sessionStore) {
        Optional<Object> optKey = this.store.get(oldSessionId);
        this.logger.debug("oldSessionId: {} -> key: {}", (Object)oldSessionId, (Object)optKey);
        if (optKey.isPresent()) {
            String key = (String)optKey.get();
            this.store.remove(key);
            this.store.remove(oldSessionId);
            this.recordSession(context, sessionStore, key);
        }
    }

    public Store<String, Object> getStore() {
        return this.store;
    }

    public void setStore(Store<String, Object> store) {
        this.store = store;
    }

    public boolean isDestroySession() {
        return this.destroySession;
    }

    public void setDestroySession(boolean destroySession) {
        this.destroySession = destroySession;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "store", this.store, "destroySession", this.destroySession);
    }
}

