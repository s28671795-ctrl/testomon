/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.util.generator;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;

@FunctionalInterface
public interface ValueGenerator {
    public String generateValue(WebContext var1, SessionStore var2);
}

