/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.matching.matcher;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;

@FunctionalInterface
public interface Matcher {
    public boolean matches(WebContext var1, SessionStore var2);
}

