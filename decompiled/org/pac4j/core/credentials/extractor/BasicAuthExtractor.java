/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.credentials.extractor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.extractor.CredentialsExtractor;
import org.pac4j.core.credentials.extractor.HeaderExtractor;
import org.pac4j.core.exception.CredentialsException;

public class BasicAuthExtractor
implements CredentialsExtractor {
    private final HeaderExtractor extractor;

    public BasicAuthExtractor() {
        this("Authorization", "Basic ");
    }

    public BasicAuthExtractor(String headerName, String prefixHeader) {
        this.extractor = new HeaderExtractor(headerName, prefixHeader);
    }

    @Override
    public Optional<Credentials> extract(WebContext context, SessionStore sessionStore) {
        Optional<Credentials> optCredentials = this.extractor.extract(context, sessionStore);
        return optCredentials.map(cred -> {
            byte[] decoded;
            TokenCredentials credentials = (TokenCredentials)cred;
            try {
                decoded = Base64.getDecoder().decode(credentials.getToken());
            }
            catch (IllegalArgumentException e) {
                throw new CredentialsException("Bad format of the basic auth header");
            }
            String token = new String(decoded, StandardCharsets.UTF_8);
            int delim = token.indexOf(":");
            if (delim < 0) {
                throw new CredentialsException("Bad format of the basic auth header");
            }
            return new UsernamePasswordCredentials(token.substring(0, delim), token.substring(delim + 1));
        });
    }
}

