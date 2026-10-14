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
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.HttpActionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CasLogoutActionBuilder
implements LogoutActionBuilder {
    private static final Logger logger = LoggerFactory.getLogger(CasLogoutActionBuilder.class);
    private final String serverLogoutUrl;
    private final String postLogoutUrlParameter;

    public CasLogoutActionBuilder(String serverLogoutUrl, String postLogoutUrlParameter) {
        if (CommonHelper.isNotBlank(serverLogoutUrl)) {
            CommonHelper.assertNotBlank("postLogoutUrlParameter", postLogoutUrlParameter);
        }
        this.serverLogoutUrl = serverLogoutUrl;
        this.postLogoutUrlParameter = postLogoutUrlParameter;
    }

    @Override
    public Optional<RedirectionAction> getLogoutAction(WebContext context, SessionStore sessionStore, UserProfile currentProfile, String targetUrl) {
        if (CommonHelper.isBlank(this.serverLogoutUrl)) {
            return Optional.empty();
        }
        String redirectUrl = this.serverLogoutUrl;
        if (CommonHelper.isNotBlank(targetUrl)) {
            redirectUrl = CommonHelper.addParameter(redirectUrl, this.postLogoutUrlParameter, targetUrl);
        }
        logger.debug("redirectUrl: {}", (Object)redirectUrl);
        return Optional.of(HttpActionHelper.buildRedirectUrlAction(context, redirectUrl));
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "serverLogoutUrl", this.serverLogoutUrl, "postLogoutUrlParameter", this.postLogoutUrlParameter);
    }
}

