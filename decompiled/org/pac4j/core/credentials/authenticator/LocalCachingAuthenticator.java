/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.credentials.authenticator;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.store.GuavaStore;
import org.pac4j.core.store.Store;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.InitializableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalCachingAuthenticator
extends InitializableObject
implements Authenticator {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Authenticator delegate;
    private int cacheSize;
    private int timeout;
    private TimeUnit timeUnit;
    private Store<Credentials, UserProfile> store;

    public LocalCachingAuthenticator() {
    }

    public LocalCachingAuthenticator(Authenticator delegate, Store<Credentials, UserProfile> store) {
        this.delegate = delegate;
        this.store = store;
    }

    public LocalCachingAuthenticator(Authenticator delegate, int cacheSize, int timeout2, TimeUnit timeUnit) {
        this.delegate = delegate;
        this.cacheSize = cacheSize;
        this.timeout = timeout2;
        this.timeUnit = timeUnit;
    }

    @Override
    public void validate(Credentials credentials, WebContext context, SessionStore sessionStore) {
        this.init();
        Optional<UserProfile> optProfile = this.store.get(credentials);
        if (optProfile.isEmpty()) {
            this.logger.debug("No cached credentials found. Delegating authentication to {}...", (Object)this.delegate);
            this.delegate.validate(credentials, context, sessionStore);
            UserProfile profile = credentials.getUserProfile();
            this.logger.debug("Caching credential. Using profile {}...", (Object)profile);
            this.store.set(credentials, profile);
        } else {
            credentials.setUserProfile(optProfile.get());
            this.logger.debug("Found cached credential. Using cached profile {}...", (Object)optProfile.get());
        }
    }

    @Override
    protected void internalInit(boolean forceReinit) {
        if (this.store == null) {
            this.store = new GuavaStore<Credentials, UserProfile>(this.cacheSize, this.timeout, this.timeUnit);
        }
        if (this.delegate instanceof InitializableObject) {
            ((InitializableObject)((Object)this.delegate)).init(forceReinit);
        }
    }

    public void removeFromCache(Credentials credentials) {
        this.store.remove(credentials);
    }

    public boolean isCached(Credentials credentials) {
        return this.store.get(credentials).isPresent();
    }

    public Authenticator getDelegate() {
        return this.delegate;
    }

    public void setDelegate(Authenticator delegate) {
        this.delegate = delegate;
    }

    public int getCacheSize() {
        return this.cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout2) {
        this.timeout = timeout2;
    }

    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Store<Credentials, UserProfile> getStore() {
        return this.store;
    }

    public void setStore(Store<Credentials, UserProfile> store) {
        this.store = store;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "delegate", this.delegate, "store", this.store);
    }
}

