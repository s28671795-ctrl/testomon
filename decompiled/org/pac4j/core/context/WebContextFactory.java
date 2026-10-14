/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.context;

import org.pac4j.core.context.WebContext;

@FunctionalInterface
public interface WebContextFactory {
    public WebContext newContext(Object ... var1);
}

