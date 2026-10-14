/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.ldaptive.LdapException;
import org.ldaptive.ResultCode;
import org.ldaptive.auth.AuthenticationResponse;
import org.ldaptive.auth.AuthenticationResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AggregateAuthenticationResponseHandler
implements AuthenticationResponseHandler {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<String, AuthenticationResponseHandler[]> responseHandlers = new HashMap<String, AuthenticationResponseHandler[]>();

    public AggregateAuthenticationResponseHandler() {
    }

    public AggregateAuthenticationResponseHandler(Map<String, AuthenticationResponseHandler[]> handlers) {
        this.setAuthenticationResponseHandlers(handlers);
    }

    public Map<String, AuthenticationResponseHandler[]> getAuthenticationResponseHandlers() {
        return Collections.unmodifiableMap(this.responseHandlers);
    }

    public void setAuthenticationResponseHandlers(Map<String, AuthenticationResponseHandler[]> handlers) {
        this.logger.trace("setting authenticationResponseHandlers: {}", (Object)handlers);
        this.responseHandlers = handlers;
    }

    public void addAuthenticationResponseHandlers(String label, AuthenticationResponseHandler ... handlers) {
        this.logger.trace("adding authenticationResponseHandlers: {}:{}", (Object)label, (Object)Arrays.toString(handlers));
        this.responseHandlers.put(label, handlers);
    }

    @Override
    public void handle(AuthenticationResponse response) throws LdapException {
        String[] labeledDn = response.getResolvedDn().split(":", 2);
        AuthenticationResponseHandler[] handlers = this.responseHandlers.get(labeledDn[0]);
        if (handlers == null) {
            throw new LdapException(ResultCode.PARAM_ERROR, "Could not find response handlers for label: " + labeledDn[0]);
        }
        if (handlers.length > 0) {
            for (AuthenticationResponseHandler ah : handlers) {
                ah.handle(response);
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final AggregateAuthenticationResponseHandler object = new AggregateAuthenticationResponseHandler();

        protected Builder() {
        }

        public Builder handler(String label, AuthenticationResponseHandler ... handlers) {
            this.object.addAuthenticationResponseHandlers(label, handlers);
            return this;
        }

        public AggregateAuthenticationResponseHandler build() {
            return this.object;
        }
    }
}

