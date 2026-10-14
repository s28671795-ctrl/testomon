/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.ldaptive.LdapException;
import org.ldaptive.ResultCode;
import org.ldaptive.auth.AuthenticationCriteria;
import org.ldaptive.auth.AuthenticationHandler;
import org.ldaptive.auth.AuthenticationHandlerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AggregateAuthenticationHandler
implements AuthenticationHandler {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<String, AuthenticationHandler> authenticationHandlers = new HashMap<String, AuthenticationHandler>();

    public AggregateAuthenticationHandler() {
    }

    public AggregateAuthenticationHandler(Map<String, AuthenticationHandler> handlers) {
        this.setAuthenticationHandlers(handlers);
    }

    public Map<String, AuthenticationHandler> getAuthenticationHandlers() {
        return Collections.unmodifiableMap(this.authenticationHandlers);
    }

    public void setAuthenticationHandlers(Map<String, AuthenticationHandler> handlers) {
        this.logger.trace("setting authenticationHandlers: {}", (Object)handlers);
        this.authenticationHandlers = handlers;
    }

    public void addAuthenticationHandler(String label, AuthenticationHandler handler) {
        this.logger.trace("adding authenticationHandler: {}:{}", (Object)label, (Object)handler);
        this.authenticationHandlers.put(label, handler);
    }

    @Override
    public AuthenticationHandlerResponse authenticate(AuthenticationCriteria criteria) throws LdapException {
        String[] labeledDn = criteria.getDn().split(":", 2);
        AuthenticationHandler ah = this.authenticationHandlers.get(labeledDn[0]);
        if (ah == null) {
            throw new LdapException(ResultCode.PARAM_ERROR, "Could not find authentication handler for label: " + labeledDn[0]);
        }
        return ah.authenticate(new AuthenticationCriteria(labeledDn[1], criteria.getAuthenticationRequest()));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final AggregateAuthenticationHandler object = new AggregateAuthenticationHandler();

        protected Builder() {
        }

        public Builder handler(String label, AuthenticationHandler handler) {
            this.object.addAuthenticationHandler(label, handler);
            return this;
        }

        public AggregateAuthenticationHandler build() {
            return this.object;
        }
    }
}

