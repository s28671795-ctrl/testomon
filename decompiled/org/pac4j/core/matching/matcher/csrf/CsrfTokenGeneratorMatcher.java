/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.matching.matcher.csrf;

import org.pac4j.core.context.Cookie;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.matching.matcher.Matcher;
import org.pac4j.core.matching.matcher.csrf.CsrfTokenGenerator;
import org.pac4j.core.util.CommonHelper;

public class CsrfTokenGeneratorMatcher
implements Matcher {
    private CsrfTokenGenerator csrfTokenGenerator;
    private String domain;
    private String path = "/";
    private Boolean httpOnly = true;
    private Boolean secure = true;
    private Integer maxAge;
    private String sameSitePolicy;
    private boolean addTokenAsAttribute = true;
    private boolean addTokenAsHeader = false;
    private boolean addTokenAsCookie = true;

    public CsrfTokenGeneratorMatcher(CsrfTokenGenerator csrfTokenGenerator) {
        this.csrfTokenGenerator = csrfTokenGenerator;
    }

    @Override
    public boolean matches(WebContext context, SessionStore sessionStore) {
        CommonHelper.assertNotNull("csrfTokenGenerator", this.csrfTokenGenerator);
        if (this.addTokenAsAttribute || this.addTokenAsHeader || this.addTokenAsCookie) {
            String token = this.csrfTokenGenerator.get(context, sessionStore);
            if (this.addTokenAsAttribute) {
                context.setRequestAttribute("pac4jCsrfToken", token);
            }
            if (this.addTokenAsHeader) {
                context.setResponseHeader("pac4jCsrfToken", token);
            }
            if (this.addTokenAsCookie) {
                Cookie cookie = new Cookie("pac4jCsrfToken", token);
                if (CommonHelper.isNotBlank(this.domain)) {
                    cookie.setDomain(this.domain);
                } else {
                    cookie.setDomain(context.getServerName());
                }
                if (CommonHelper.isNotBlank(this.path)) {
                    cookie.setPath(this.path);
                }
                if (this.httpOnly != null) {
                    cookie.setHttpOnly(this.httpOnly);
                }
                if (this.secure != null) {
                    cookie.setSecure(this.secure);
                }
                if (this.maxAge != null) {
                    cookie.setMaxAge(this.maxAge);
                }
                if (CommonHelper.isNotBlank(this.sameSitePolicy)) {
                    cookie.setSameSitePolicy(this.sameSitePolicy);
                }
                context.addResponseCookie(cookie);
            }
        }
        return true;
    }

    public CsrfTokenGenerator getCsrfTokenGenerator() {
        return this.csrfTokenGenerator;
    }

    public void setCsrfTokenGenerator(CsrfTokenGenerator csrfTokenGenerator) {
        this.csrfTokenGenerator = csrfTokenGenerator;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getHttpOnly() {
        return this.httpOnly;
    }

    public void setHttpOnly(Boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public Boolean getSecure() {
        return this.secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public Integer getMaxAge() {
        return this.maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public String getSameSitePolicy() {
        return this.sameSitePolicy;
    }

    public void setSameSitePolicy(String sameSitePolicy) {
        this.sameSitePolicy = sameSitePolicy;
    }

    public boolean isAddTokenAsAttribute() {
        return this.addTokenAsAttribute;
    }

    public void setAddTokenAsAttribute(boolean addTokenAsAttribute) {
        this.addTokenAsAttribute = addTokenAsAttribute;
    }

    public boolean isAddTokenAsHeader() {
        return this.addTokenAsHeader;
    }

    public void setAddTokenAsHeader(boolean addTokenAsHeader) {
        this.addTokenAsHeader = addTokenAsHeader;
    }

    public boolean isAddTokenAsCookie() {
        return this.addTokenAsCookie;
    }

    public void setAddTokenAsCookie(boolean addTokenAsCookie) {
        this.addTokenAsCookie = addTokenAsCookie;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "csrfTokenGenerator", this.csrfTokenGenerator, "domain", this.domain, "path", this.path, "httpOnly", this.httpOnly, "secure", this.secure, "maxAge", this.maxAge, "addTokenAsAttribute", this.addTokenAsAttribute, "addTokenAsHeader", this.addTokenAsHeader, "addTokenAsCookie", this.addTokenAsCookie);
    }
}

