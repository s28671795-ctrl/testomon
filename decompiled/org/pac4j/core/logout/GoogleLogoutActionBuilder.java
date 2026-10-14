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
import org.pac4j.core.util.HttpActionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoogleLogoutActionBuilder
implements LogoutActionBuilder {
    private static final Logger logger = LoggerFactory.getLogger(GoogleLogoutActionBuilder.class);

    @Override
    public Optional<RedirectionAction> getLogoutAction(WebContext context, SessionStore sessionStore, UserProfile currentProfile, String targetUrl) {
        String redirectUrl = "https://accounts.google.com/Logout";
        logger.debug("redirectUrl: {}", (Object)"https://accounts.google.com/Logout");
        return Optional.of(HttpActionHelper.buildRedirectUrlAction(context, "https://accounts.google.com/Logout"));
    }
}

