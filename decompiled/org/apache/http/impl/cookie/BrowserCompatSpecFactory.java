/*
 * Decompiled with CFR 0.152.
 */
package org.apache.http.impl.cookie;

import java.util.Collection;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class BrowserCompatSpecFactory
implements CookieSpecFactory,
CookieSpecProvider {
    private final SecurityLevel securityLevel;
    private final CookieSpec cookieSpec;

    public BrowserCompatSpecFactory(String[] datepatterns, SecurityLevel securityLevel) {
        this.securityLevel = securityLevel;
        this.cookieSpec = new BrowserCompatSpec(datepatterns, securityLevel);
    }

    public BrowserCompatSpecFactory(String[] datepatterns) {
        this(null, SecurityLevel.SECURITYLEVEL_DEFAULT);
    }

    public BrowserCompatSpecFactory() {
        this(null, SecurityLevel.SECURITYLEVEL_DEFAULT);
    }

    @Override
    public CookieSpec newInstance(HttpParams params) {
        if (params != null) {
            String[] patterns = null;
            Collection param2 = (Collection)params.getParameter("http.protocol.cookie-datepatterns");
            if (param2 != null) {
                patterns = new String[param2.size()];
                patterns = param2.toArray(patterns);
            }
            return new BrowserCompatSpec(patterns, this.securityLevel);
        }
        return new BrowserCompatSpec(null, this.securityLevel);
    }

    @Override
    public CookieSpec create(HttpContext context) {
        return this.cookieSpec;
    }

    public static enum SecurityLevel {
        SECURITYLEVEL_DEFAULT,
        SECURITYLEVEL_IE_MEDIUM;

    }
}

