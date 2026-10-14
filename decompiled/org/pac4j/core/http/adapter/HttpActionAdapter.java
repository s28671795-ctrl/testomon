/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.http.adapter;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.http.HttpAction;

@FunctionalInterface
public interface HttpActionAdapter {
    public Object adapt(HttpAction var1, WebContext var2);
}

