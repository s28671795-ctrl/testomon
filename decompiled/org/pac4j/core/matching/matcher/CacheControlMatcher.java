/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.matching.matcher;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.matching.matcher.Matcher;
import org.pac4j.core.util.CommonHelper;

public class CacheControlMatcher
implements Matcher {
    @Override
    public boolean matches(WebContext context, SessionStore sessionStore) {
        String url2 = context.getFullRequestURL().toLowerCase();
        if (!(url2.endsWith(".css") || url2.endsWith(".js") || url2.endsWith(".png") || url2.endsWith(".jpg") || url2.endsWith(".ico") || url2.endsWith(".jpeg") || url2.endsWith(".bmp") || url2.endsWith(".gif"))) {
            context.setResponseHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
            context.setResponseHeader("Pragma", "no-cache");
            context.setResponseHeader("Expires", "0");
        }
        return true;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), new Object[0]);
    }
}

