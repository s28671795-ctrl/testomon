/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.http.callback;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.http.url.UrlResolver;

public interface CallbackUrlResolver {
    public String compute(UrlResolver var1, String var2, String var3, WebContext var4);

    public boolean matches(String var1, WebContext var2);
}

