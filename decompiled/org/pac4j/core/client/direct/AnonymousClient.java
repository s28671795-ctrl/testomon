/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.client.direct;

import java.util.Optional;
import org.pac4j.core.client.DirectClient;
import org.pac4j.core.credentials.AnonymousCredentials;
import org.pac4j.core.profile.AnonymousProfile;

public final class AnonymousClient
extends DirectClient {
    public static final AnonymousClient INSTANCE = new AnonymousClient();
    private static boolean warned;

    public AnonymousClient() {
        if (!warned) {
            this.logger.warn("Be careful when using the 'AnonymousClient': an 'AnonymousProfile' is returned and the access is granted for the request.");
            warned = true;
        }
    }

    @Override
    protected void internalInit(boolean forceReinit) {
        this.defaultCredentialsExtractor((ctx, store) -> Optional.of(AnonymousCredentials.INSTANCE));
        this.defaultAuthenticator((cred, ctx, store) -> cred.setUserProfile(AnonymousProfile.INSTANCE));
    }
}

