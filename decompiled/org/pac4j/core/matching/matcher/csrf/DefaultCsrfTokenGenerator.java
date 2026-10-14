/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.matching.matcher.csrf;

import java.util.Date;
import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.matching.matcher.csrf.CsrfTokenGenerator;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCsrfTokenGenerator
implements CsrfTokenGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCsrfTokenGenerator.class);
    private int ttlInSeconds = 14400;
    private boolean rotateTokens = true;

    @Override
    public String get(WebContext context, SessionStore sessionStore) {
        String token = null;
        Optional<Object> optCurrentToken = sessionStore.get(context, "pac4jCsrfToken");
        if (optCurrentToken.isPresent()) {
            token = (String)optCurrentToken.get();
            LOGGER.debug("previous CSRF token: {}", (Object)token);
            sessionStore.set(context, "pac4jPreviousCsrfToken", token);
        } else {
            sessionStore.set(context, "pac4jPreviousCsrfToken", null);
        }
        if (optCurrentToken.isEmpty() || this.rotateTokens) {
            token = CommonHelper.randomString(32);
            LOGGER.debug("generated CSRF token: {} for current URL: {}", (Object)token, (Object)context.getFullRequestURL());
            long expirationDate = new Date().getTime() + (long)(this.ttlInSeconds * 1000);
            sessionStore.set(context, "pac4jCsrfToken", token);
            sessionStore.set(context, "pac4jCsrfTokenExpirationDate", expirationDate);
        }
        return token;
    }

    public int getTtlInSeconds() {
        return this.ttlInSeconds;
    }

    public void setTtlInSeconds(int ttlInSeconds) {
        this.ttlInSeconds = ttlInSeconds;
    }

    public boolean isRotateTokens() {
        return this.rotateTokens;
    }

    public void setRotateTokens(boolean rotateTokens) {
        this.rotateTokens = rotateTokens;
    }
}

