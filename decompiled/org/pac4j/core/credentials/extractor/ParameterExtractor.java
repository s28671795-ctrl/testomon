/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.credentials.extractor;

import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.WebContextHelper;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.credentials.extractor.CredentialsExtractor;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.util.CommonHelper;

public class ParameterExtractor
implements CredentialsExtractor {
    private final String parameterName;
    private boolean supportGetRequest;
    private boolean supportPostRequest;

    public ParameterExtractor(String parameterName) {
        this(parameterName, false, true);
    }

    public ParameterExtractor(String parameterName, boolean supportGetRequest, boolean supportPostRequest) {
        this.parameterName = parameterName;
        this.supportGetRequest = supportGetRequest;
        this.supportPostRequest = supportPostRequest;
    }

    @Override
    public Optional<Credentials> extract(WebContext context, SessionStore sessionStore) {
        if (WebContextHelper.isGet(context) && !this.supportGetRequest) {
            throw new CredentialsException("GET requests not supported");
        }
        if (WebContextHelper.isPost(context) && !this.supportPostRequest) {
            throw new CredentialsException("POST requests not supported");
        }
        Optional<String> value = context.getRequestParameter(this.parameterName);
        if (!value.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(new TokenCredentials(value.get()));
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "parameterName", this.parameterName, "supportGetRequest", this.supportGetRequest, "supportPostRequest", this.supportPostRequest);
    }
}

