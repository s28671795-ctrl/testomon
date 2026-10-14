/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.authorization.generator;

import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.profile.UserProfile;

@FunctionalInterface
public interface AuthorizationGenerator {
    public Optional<UserProfile> generate(WebContext var1, SessionStore var2, UserProfile var3);
}

