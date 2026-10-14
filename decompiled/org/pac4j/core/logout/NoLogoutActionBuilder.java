/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.logout;

import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.exception.http.RedirectionAction;
import org.pac4j.core.logout.LogoutActionBuilder;
import org.pac4j.core.profile.UserProfile;

public class NoLogoutActionBuilder
implements LogoutActionBuilder {
    public static final NoLogoutActionBuilder INSTANCE = new NoLogoutActionBuilder();

    @Override
    public Optional<RedirectionAction> getLogoutAction(WebContext context, SessionStore sessionStore, UserProfile currentProfile, String targetUrl) {
        return Optional.empty();
    }
}

