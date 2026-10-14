/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.redirect;

import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.exception.http.RedirectionAction;

@FunctionalInterface
public interface RedirectionActionBuilder {
    public static final String ATTRIBUTE_FORCE_AUTHN = "ForceAuthn";
    public static final String ATTRIBUTE_PASSIVE = "Passive";

    public Optional<RedirectionAction> getRedirectionAction(WebContext var1, SessionStore var2);
}

