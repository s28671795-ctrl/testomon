/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.credentials.extractor;

import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.credentials.extractor.CredentialsExtractor;
import org.pac4j.core.credentials.extractor.HeaderExtractor;

public class BearerAuthExtractor
implements CredentialsExtractor {
    private final HeaderExtractor extractor;

    public BearerAuthExtractor() {
        this("Authorization", "Bearer ");
    }

    public BearerAuthExtractor(String headerName, String prefixHeader) {
        this.extractor = new HeaderExtractor(headerName, prefixHeader);
    }

    @Override
    public Optional<Credentials> extract(WebContext context, SessionStore sessionStore) {
        return this.extractor.extract(context, sessionStore);
    }
}

