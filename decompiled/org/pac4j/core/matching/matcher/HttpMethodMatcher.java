/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.matching.matcher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.matching.matcher.Matcher;
import org.pac4j.core.util.CommonHelper;

public class HttpMethodMatcher
implements Matcher {
    private Set<HttpConstants.HTTP_METHOD> methods;

    public HttpMethodMatcher() {
    }

    public HttpMethodMatcher(HttpConstants.HTTP_METHOD ... methods) {
        if (methods != null) {
            this.methods = new HashSet<HttpConstants.HTTP_METHOD>(Arrays.asList(methods));
        }
    }

    @Override
    public boolean matches(WebContext context, SessionStore sessionStore) {
        CommonHelper.assertNotNull("methods", this.methods);
        String requestMethod = context.getRequestMethod();
        for (HttpConstants.HTTP_METHOD method2 : this.methods) {
            if (!method2.name().equalsIgnoreCase(requestMethod)) continue;
            return true;
        }
        return false;
    }

    public Set<HttpConstants.HTTP_METHOD> getMethods() {
        return this.methods;
    }

    public void setMethods(Set<HttpConstants.HTTP_METHOD> methods) {
        this.methods = methods;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "methods", this.methods);
    }
}

