/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.http.url;

import org.pac4j.core.context.WebContext;

@FunctionalInterface
public interface UrlResolver {
    public String compute(String var1, WebContext var2);
}

