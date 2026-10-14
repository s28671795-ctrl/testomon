/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.credentials.extractor;

import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;

@FunctionalInterface
public interface CredentialsExtractor {
    public Optional<Credentials> extract(WebContext var1, SessionStore var2);
}

