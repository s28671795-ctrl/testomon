/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.http.callback;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.http.callback.CallbackUrlResolver;
import org.pac4j.core.http.url.UrlResolver;

public class NoParameterCallbackUrlResolver
implements CallbackUrlResolver {
    @Override
    public String compute(UrlResolver urlResolver, String url2, String clientName, WebContext context) {
        return urlResolver.compute(url2, context);
    }

    @Override
    public boolean matches(String clientName, WebContext context) {
        return false;
    }
}

