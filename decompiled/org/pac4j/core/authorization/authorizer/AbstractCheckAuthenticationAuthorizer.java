/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.authorizer;

import org.pac4j.core.authorization.authorizer.ProfileAuthorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.HttpActionHelper;

public abstract class AbstractCheckAuthenticationAuthorizer
extends ProfileAuthorizer {
    private String redirectionUrl;

    public AbstractCheckAuthenticationAuthorizer() {
    }

    public AbstractCheckAuthenticationAuthorizer(String redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
    }

    @Override
    protected boolean handleError(WebContext context, SessionStore sessionStore) {
        if (this.redirectionUrl != null) {
            throw HttpActionHelper.buildRedirectUrlAction(context, this.redirectionUrl);
        }
        return false;
    }

    public String getRedirectionUrl() {
        return this.redirectionUrl;
    }

    public void setRedirectionUrl(String redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "redirectionUrl", this.redirectionUrl);
    }
}

