/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.credentials.extractor;

import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.extractor.CredentialsExtractor;

public class FormExtractor
implements CredentialsExtractor {
    private final String usernameParameter;
    private final String passwordParameter;

    public FormExtractor(String usernameParameter, String passwordParameter) {
        this.usernameParameter = usernameParameter;
        this.passwordParameter = passwordParameter;
    }

    @Override
    public Optional<Credentials> extract(WebContext context, SessionStore sessionStore) {
        Optional<String> username = context.getRequestParameter(this.usernameParameter);
        Optional<String> password = context.getRequestParameter(this.passwordParameter);
        if (!username.isPresent() || !password.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(new UsernamePasswordCredentials(username.get(), password.get()));
    }

    public String getUsernameParameter() {
        return this.usernameParameter;
    }

    public String getPasswordParameter() {
        return this.passwordParameter;
    }
}

