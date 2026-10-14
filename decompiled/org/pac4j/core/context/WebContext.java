/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.context;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import org.pac4j.core.context.Cookie;
import org.pac4j.core.exception.TechnicalException;

public interface WebContext {
    public Optional<String> getRequestParameter(String var1);

    public Map<String, String[]> getRequestParameters();

    public Optional getRequestAttribute(String var1);

    public void setRequestAttribute(String var1, Object var2);

    public Optional<String> getRequestHeader(String var1);

    public String getRequestMethod();

    public String getRemoteAddr();

    public void setResponseHeader(String var1, String var2);

    public Optional<String> getResponseHeader(String var1);

    public void setResponseContentType(String var1);

    public String getServerName();

    public int getServerPort();

    public String getScheme();

    public boolean isSecure();

    public String getFullRequestURL();

    default public String getRequestURL() {
        int idx = this.getFullRequestURL().indexOf(63);
        if (idx != -1) {
            return this.getFullRequestURL().substring(0, idx);
        }
        return this.getFullRequestURL();
    }

    public Collection<Cookie> getRequestCookies();

    public void addResponseCookie(Cookie var1);

    public String getPath();

    default public String getRequestContent() {
        throw new TechnicalException("Operation not supported");
    }

    default public String getProtocol() {
        return "HTTP/1.0";
    }
}

