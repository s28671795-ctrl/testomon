/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.matching.matcher;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.matching.matcher.Matcher;
import org.pac4j.core.util.CommonHelper;

public class XSSProtectionMatcher
implements Matcher {
    @Override
    public boolean matches(WebContext context, SessionStore sessionStore) {
        context.setResponseHeader("X-XSS-Protection", "1; mode=block");
        return true;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), new Object[0]);
    }
}

