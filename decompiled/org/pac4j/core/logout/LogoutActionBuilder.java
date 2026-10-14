/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.logout;

import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.exception.http.RedirectionAction;
import org.pac4j.core.profile.UserProfile;

@FunctionalInterface
public interface LogoutActionBuilder {
    public Optional<RedirectionAction> getLogoutAction(WebContext var1, SessionStore var2, UserProfile var3, String var4);
}

