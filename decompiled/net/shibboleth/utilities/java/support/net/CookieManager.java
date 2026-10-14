/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package net.shibboleth.utilities.java.support.net;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public final class CookieManager
extends AbstractInitializableComponent {
    @Nullable
    private String cookiePath;
    @Nullable
    private String cookieDomain;
    @NonnullAfterInit
    private HttpServletRequest httpRequest;
    @NonnullAfterInit
    private HttpServletResponse httpResponse;
    private boolean secure = true;
    private boolean httpOnly = true;
    private int maxAge = -1;

    public void setCookiePath(@Nullable String path) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.cookiePath = StringSupport.trimOrNull(path);
    }

    public void setCookieDomain(@Nullable String domain) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.cookieDomain = StringSupport.trimOrNull(domain);
    }

    public void setHttpServletRequest(@Nonnull HttpServletRequest request) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.httpRequest = Constraint.isNotNull(request, "HttpServletRequest cannot be null");
    }

    public void setHttpServletResponse(@Nonnull HttpServletResponse response) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.httpResponse = Constraint.isNotNull(response, "HttpServletResponse cannot be null");
    }

    public void setSecure(boolean flag) {
        this.secure = flag;
    }

    public void setHttpOnly(boolean flag) {
        this.httpOnly = flag;
    }

    public void setMaxAge(int age) {
        this.maxAge = age;
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.httpRequest == null || this.httpResponse == null) {
            throw new ComponentInitializationException("Servlet request and response must be set");
        }
    }

    @Nullable
    public void addCookie(@Nonnull @NotEmpty String name, @Nonnull @NotEmpty String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(this.cookiePath != null ? this.cookiePath : this.contextPathToCookiePath());
        if (this.cookieDomain != null) {
            cookie.setDomain(this.cookieDomain);
        }
        cookie.setSecure(this.secure);
        cookie.setHttpOnly(this.httpOnly);
        cookie.setMaxAge(this.maxAge);
        this.httpResponse.addCookie(cookie);
    }

    @Nullable
    public void unsetCookie(@Nonnull @NotEmpty String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath(this.cookiePath != null ? this.cookiePath : this.contextPathToCookiePath());
        if (this.cookieDomain != null) {
            cookie.setDomain(this.cookieDomain);
        }
        cookie.setSecure(this.secure);
        cookie.setHttpOnly(this.httpOnly);
        cookie.setMaxAge(0);
        this.httpResponse.addCookie(cookie);
    }

    public boolean cookieHasValue(@Nonnull @NotEmpty String name, @Nonnull @NotEmpty String expectedValue) {
        String realValue = this.getCookieValue(name, null);
        if (realValue == null) {
            return false;
        }
        return realValue.equals(expectedValue);
    }

    @Nullable
    public String getCookieValue(@Nonnull @NotEmpty String name, @Nullable String defValue) {
        Cookie[] cookies = this.httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (!cookie.getName().equals(name)) continue;
                return cookie.getValue();
            }
        }
        return defValue;
    }

    @Nonnull
    @NotEmpty
    private String contextPathToCookiePath() {
        return "".equals(this.httpRequest.getContextPath()) ? "/" : this.httpRequest.getContextPath();
    }
}

