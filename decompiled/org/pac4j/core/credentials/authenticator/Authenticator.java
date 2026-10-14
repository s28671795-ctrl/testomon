/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.credentials.authenticator;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;

@FunctionalInterface
public interface Authenticator {
    public static final Authenticator ALWAYS_VALIDATE = new Authenticator(){

        @Override
        public void validate(Credentials credentials, WebContext context, SessionStore sessionStore) {
        }
    };

    public void validate(Credentials var1, WebContext var2, SessionStore var3);
}

