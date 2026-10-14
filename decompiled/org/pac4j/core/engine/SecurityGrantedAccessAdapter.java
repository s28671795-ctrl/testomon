/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.engine;

import java.util.Collection;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;

@FunctionalInterface
public interface SecurityGrantedAccessAdapter {
    public Object adapt(WebContext var1, SessionStore var2, Collection<UserProfile> var3, Object ... var4) throws Exception;
}

