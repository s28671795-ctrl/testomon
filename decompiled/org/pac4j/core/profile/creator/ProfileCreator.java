/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.creator;

import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.profile.UserProfile;

@FunctionalInterface
public interface ProfileCreator {
    public Optional<UserProfile> create(Credentials var1, WebContext var2, SessionStore var3);
}

