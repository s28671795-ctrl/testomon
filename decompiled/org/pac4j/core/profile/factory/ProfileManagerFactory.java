/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.factory;

import java.util.function.BiFunction;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.ProfileManager;

@FunctionalInterface
public interface ProfileManagerFactory
extends BiFunction<WebContext, SessionStore, ProfileManager> {
    public static final ProfileManagerFactory DEFAULT = ProfileManager::new;
}

