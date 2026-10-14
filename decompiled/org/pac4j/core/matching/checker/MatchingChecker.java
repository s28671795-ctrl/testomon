/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.matching.checker;

import java.util.List;
import java.util.Map;
import org.pac4j.core.client.Client;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.matching.matcher.Matcher;

@FunctionalInterface
public interface MatchingChecker {
    public boolean matches(WebContext var1, SessionStore var2, String var3, Map<String, Matcher> var4, List<Client> var5);
}

