/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.matching.matcher.csrf;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;

@FunctionalInterface
public interface CsrfTokenGenerator {
    public String get(WebContext var1, SessionStore var2);
}

