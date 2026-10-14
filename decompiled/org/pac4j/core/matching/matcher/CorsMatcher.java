/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.matching.matcher;

import java.util.Set;
import java.util.stream.Collectors;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.matching.matcher.Matcher;
import org.pac4j.core.util.CommonHelper;

public class CorsMatcher
implements Matcher {
    private String allowOrigin;
    private String exposeHeaders;
    private int maxAge = -1;
    private Boolean allowCredentials;
    private Set<HttpConstants.HTTP_METHOD> allowMethods;
    private String allowHeaders;

    @Override
    public boolean matches(WebContext context, SessionStore sessionStore) {
        CommonHelper.assertNotBlank("allowOrigin", this.allowOrigin);
        context.setResponseHeader("Access-Control-Allow-Origin", this.allowOrigin);
        if (CommonHelper.isNotBlank(this.exposeHeaders)) {
            context.setResponseHeader("Access-Control-Expose-Headers", this.exposeHeaders);
        }
        if (this.maxAge != -1) {
            context.setResponseHeader("Access-Control-Max-Age", "" + this.maxAge);
        }
        if (this.allowCredentials != null && this.allowCredentials.booleanValue()) {
            context.setResponseHeader("Access-Control-Allow-Credentials", this.allowCredentials.toString());
        }
        if (this.allowMethods != null) {
            String methods = this.allowMethods.stream().map(Enum::toString).collect(Collectors.joining(", "));
            context.setResponseHeader("Access-Control-Allow-Methods", methods);
        }
        if (CommonHelper.isNotBlank(this.allowHeaders)) {
            context.setResponseHeader("Access-Control-Allow-Headers", this.allowHeaders);
        }
        return true;
    }

    public String getAllowOrigin() {
        return this.allowOrigin;
    }

    public void setAllowOrigin(String allowOrigin) {
        this.allowOrigin = allowOrigin;
    }

    public String getExposeHeaders() {
        return this.exposeHeaders;
    }

    public void setExposeHeaders(String exposeHeaders) {
        this.exposeHeaders = exposeHeaders;
    }

    public int getMaxAge() {
        return this.maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public Boolean getAllowCredentials() {
        return this.allowCredentials;
    }

    public void setAllowCredentials(Boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public Set<HttpConstants.HTTP_METHOD> getAllowMethods() {
        return this.allowMethods;
    }

    public void setAllowMethods(Set<HttpConstants.HTTP_METHOD> allowMethods) {
        this.allowMethods = allowMethods;
    }

    public String getAllowHeaders() {
        return this.allowHeaders;
    }

    public void setAllowHeaders(String allowHeaders) {
        this.allowHeaders = allowHeaders;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "allowOrigin", this.allowOrigin, "exposeHeaders", this.exposeHeaders, "maxAge", this.maxAge, "allowCredentials", this.allowCredentials, "allowMethods", this.allowMethods, "allowHeaders", this.allowHeaders);
    }
}

