/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.creator;

import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.profile.creator.ProfileCreator;

public class AuthenticatorProfileCreator
implements ProfileCreator {
    public static final AuthenticatorProfileCreator INSTANCE = new AuthenticatorProfileCreator();

    @Override
    public Optional<UserProfile> create(Credentials credentials, WebContext context, SessionStore sessionStore) {
        return Optional.ofNullable(credentials.getUserProfile());
    }
}

