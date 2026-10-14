/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.http.callback;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.http.callback.CallbackUrlResolver;
import org.pac4j.core.http.url.UrlResolver;
import org.pac4j.core.util.CommonHelper;

public class PathParameterCallbackUrlResolver
implements CallbackUrlResolver {
    @Override
    public String compute(UrlResolver urlResolver, String url2, String clientName, WebContext context) {
        Object newUrl = urlResolver.compute(url2, context);
        if (newUrl != null) {
            if (!((String)newUrl).endsWith("/")) {
                newUrl = (String)newUrl + "/";
            }
            newUrl = (String)newUrl + clientName;
        }
        return newUrl;
    }

    @Override
    public boolean matches(String clientName, WebContext context) {
        String path = context.getPath();
        if (path != null) {
            int pos = path.lastIndexOf("/");
            String name = pos >= 0 ? path.substring(pos + 1) : path;
            return CommonHelper.areEqualsIgnoreCaseAndTrim(name, clientName);
        }
        return false;
    }
}

