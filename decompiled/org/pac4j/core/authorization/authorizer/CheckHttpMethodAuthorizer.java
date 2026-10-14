/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.authorizer;

import java.util.List;
import java.util.Set;
import org.pac4j.core.authorization.authorizer.AbstractRequireAnyAuthorizer;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;

public class CheckHttpMethodAuthorizer
extends AbstractRequireAnyAuthorizer<HttpConstants.HTTP_METHOD> {
    public CheckHttpMethodAuthorizer() {
    }

    public CheckHttpMethodAuthorizer(HttpConstants.HTTP_METHOD ... methods) {
        this.setElements(methods);
    }

    public CheckHttpMethodAuthorizer(List<HttpConstants.HTTP_METHOD> methods) {
        this.setElements(methods);
    }

    public CheckHttpMethodAuthorizer(Set<HttpConstants.HTTP_METHOD> methods) {
        this.setElements(methods);
    }

    @Override
    protected boolean check(WebContext context, SessionStore sessionStore, UserProfile profile, HttpConstants.HTTP_METHOD element) {
        String requestMethod = context.getRequestMethod();
        return requestMethod.equalsIgnoreCase(element.toString());
    }

    public static CheckHttpMethodAuthorizer checkHttpMethod(HttpConstants.HTTP_METHOD ... methods) {
        return new CheckHttpMethodAuthorizer(methods);
    }

    public static CheckHttpMethodAuthorizer checkHttpMethod(List<HttpConstants.HTTP_METHOD> methods) {
        return new CheckHttpMethodAuthorizer(methods);
    }

    public static CheckHttpMethodAuthorizer checkHttpMethod(Set<HttpConstants.HTTP_METHOD> methods) {
        return new CheckHttpMethodAuthorizer(methods);
    }
}

