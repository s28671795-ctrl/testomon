/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.matching.matcher;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.WebContextHelper;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.matching.matcher.Matcher;
import org.pac4j.core.util.CommonHelper;

public class StrictTransportSecurityMatcher
implements Matcher {
    private static final int DEFAULT_MAX_AGE = 15768000;
    private int maxAge = 15768000;

    public StrictTransportSecurityMatcher() {
    }

    public StrictTransportSecurityMatcher(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public boolean matches(WebContext context, SessionStore sessionStore) {
        if (WebContextHelper.isHttpsOrSecure(context)) {
            context.setResponseHeader("Strict-Transport-Security", "max-age=" + this.maxAge + " ; includeSubDomains");
        }
        return true;
    }

    public int getMaxAge() {
        return this.maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "maxAge", this.maxAge);
    }
}

