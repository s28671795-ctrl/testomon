/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import org.ldaptive.ConnectionFactoryManager;
import org.ldaptive.Credential;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.LdapUtils;
import org.ldaptive.ReturnAttributes;
import org.ldaptive.auth.AggregateAuthenticationHandler;
import org.ldaptive.auth.AggregateDnResolver;
import org.ldaptive.auth.AggregateEntryResolver;
import org.ldaptive.auth.AuthenticationCriteria;
import org.ldaptive.auth.AuthenticationHandler;
import org.ldaptive.auth.AuthenticationHandlerResponse;
import org.ldaptive.auth.AuthenticationRequest;
import org.ldaptive.auth.AuthenticationRequestHandler;
import org.ldaptive.auth.AuthenticationResponse;
import org.ldaptive.auth.AuthenticationResponseHandler;
import org.ldaptive.auth.AuthenticationResultCode;
import org.ldaptive.auth.DnResolver;
import org.ldaptive.auth.EntryResolver;
import org.ldaptive.auth.NoOpEntryResolver;
import org.ldaptive.auth.SearchEntryResolver;
import org.ldaptive.auth.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Authenticator {
    private static final EntryResolver NOOP_RESOLVER = new NoOpEntryResolver();
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private DnResolver dnResolver;
    private AuthenticationHandler authenticationHandler;
    private EntryResolver entryResolver;
    private String[] returnAttributes;
    private AuthenticationRequestHandler[] requestHandlers;
    private AuthenticationResponseHandler[] responseHandlers;
    private boolean resolveEntryOnFailure;

    public Authenticator() {
    }

    public Authenticator(DnResolver resolver2, AuthenticationHandler handler) {
        this.setDnResolver(resolver2);
        this.setAuthenticationHandler(handler);
    }

    public DnResolver getDnResolver() {
        return this.dnResolver;
    }

    public void setDnResolver(DnResolver resolver2) {
        this.dnResolver = resolver2;
    }

    public AuthenticationHandler getAuthenticationHandler() {
        return this.authenticationHandler;
    }

    public void setAuthenticationHandler(AuthenticationHandler handler) {
        this.authenticationHandler = handler;
    }

    public EntryResolver getEntryResolver() {
        return this.entryResolver;
    }

    public void setEntryResolver(EntryResolver resolver2) {
        this.entryResolver = resolver2;
    }

    public boolean getResolveEntryOnFailure() {
        return this.resolveEntryOnFailure;
    }

    public void setResolveEntryOnFailure(boolean b) {
        this.resolveEntryOnFailure = b;
    }

    public String[] getReturnAttributes() {
        return this.returnAttributes;
    }

    public void setReturnAttributes(String ... attrs) {
        this.returnAttributes = attrs;
    }

    public AuthenticationRequestHandler[] getRequestHandlers() {
        return this.requestHandlers;
    }

    public void setRequestHandlers(AuthenticationRequestHandler ... handlers) {
        this.requestHandlers = handlers;
    }

    public AuthenticationResponseHandler[] getResponseHandlers() {
        return this.responseHandlers;
    }

    public void setResponseHandlers(AuthenticationResponseHandler ... handlers) {
        this.responseHandlers = handlers;
    }

    public String resolveDn(User user) throws LdapException {
        return this.dnResolver.resolve(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws LdapException {
        return this.authenticate(this.resolveDn(request.getUser()), request);
    }

    public void close() {
        Map<String, AuthenticationHandler> handlers;
        Map<String, Object> resolvers;
        if (this.dnResolver instanceof ConnectionFactoryManager) {
            this.closeConnectionFactoryManagers((ConnectionFactoryManager)((Object)this.dnResolver));
        } else if (this.dnResolver instanceof AggregateDnResolver && (resolvers = ((AggregateDnResolver)this.dnResolver).getDnResolvers()) != null) {
            this.closeConnectionFactoryManagers(resolvers.values().toArray(new ConnectionFactoryManager[0]));
        }
        if (this.authenticationHandler instanceof ConnectionFactoryManager) {
            this.closeConnectionFactoryManagers((ConnectionFactoryManager)((Object)this.authenticationHandler));
        } else if (this.authenticationHandler instanceof AggregateAuthenticationHandler && (handlers = ((AggregateAuthenticationHandler)this.authenticationHandler).getAuthenticationHandlers()) != null) {
            this.closeConnectionFactoryManagers(handlers.values().toArray(new ConnectionFactoryManager[0]));
        }
        if (this.entryResolver instanceof ConnectionFactoryManager) {
            this.closeConnectionFactoryManagers((ConnectionFactoryManager)((Object)this.entryResolver));
        } else if (this.entryResolver instanceof AggregateEntryResolver && (resolvers = ((AggregateEntryResolver)this.entryResolver).getEntryResolvers()) != null) {
            this.closeConnectionFactoryManagers(resolvers.values().toArray(new ConnectionFactoryManager[0]));
        }
    }

    private void closeConnectionFactoryManagers(ConnectionFactoryManager ... managers) {
        if (managers != null) {
            Arrays.stream(managers).filter(ConnectionFactoryManager.class::isInstance).map(ConnectionFactoryManager.class::cast).map(ConnectionFactoryManager::getConnectionFactory).filter(Objects::nonNull).forEach(cf -> {
                try {
                    cf.close();
                }
                catch (Exception e) {
                    this.logger.debug("Error closing connection factory {}", cf, (Object)e);
                }
            });
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected AuthenticationResponse authenticate(String dn, AuthenticationRequest request) throws LdapException {
        LdapEntry entry;
        this.logger.trace("authenticate dn={} with request={}", (Object)dn, (Object)request);
        AuthenticationResponse invalidInput = this.validateInput(dn, request);
        if (invalidInput != null) {
            return invalidInput;
        }
        AuthenticationRequest processedRequest = this.processRequest(dn, request);
        AuthenticationHandlerResponse response = null;
        try {
            AuthenticationCriteria ac = new AuthenticationCriteria(dn, processedRequest);
            response = this.getAuthenticationHandler().authenticate(ac);
            entry = this.resolveEntry(ac, response);
        }
        finally {
            if (response != null && response.getConnection() != null) {
                response.getConnection().close();
            }
        }
        this.logger.info("Authentication {} for dn: {}", (Object)(response.isSuccess() ? "succeeded" : "failed"), (Object)dn);
        AuthenticationResponse authResponse = new AuthenticationResponse(response, dn, entry);
        if (this.getResponseHandlers() != null && this.getResponseHandlers().length > 0) {
            for (AuthenticationResponseHandler ah : this.getResponseHandlers()) {
                ah.handle(authResponse);
            }
        }
        this.logger.debug("Authenticate response={} for dn={} with request={}", response, dn, processedRequest);
        return authResponse;
    }

    protected AuthenticationResponse validateInput(String dn, AuthenticationRequest request) {
        AuthenticationResponse response = null;
        Credential credential = request.getCredential();
        if (credential == null || credential.getBytes() == null) {
            response = (AuthenticationResponse)AuthenticationResponse.builder().response((AuthenticationHandlerResponse)((AuthenticationHandlerResponse.Builder)AuthenticationHandlerResponse.builder().diagnosticMessage("Credential cannot be null")).resultCode(AuthenticationResultCode.INVALID_CREDENTIAL).build()).dn(dn).build();
        } else if (credential.getBytes().length == 0) {
            response = (AuthenticationResponse)AuthenticationResponse.builder().response((AuthenticationHandlerResponse)((AuthenticationHandlerResponse.Builder)AuthenticationHandlerResponse.builder().diagnosticMessage("Credential cannot be empty")).resultCode(AuthenticationResultCode.INVALID_CREDENTIAL).build()).dn(dn).build();
        } else if (dn == null) {
            response = (AuthenticationResponse)AuthenticationResponse.builder().response((AuthenticationHandlerResponse)((AuthenticationHandlerResponse.Builder)AuthenticationHandlerResponse.builder().diagnosticMessage("DN cannot be null")).resultCode(AuthenticationResultCode.DN_RESOLUTION_FAILURE).build()).dn(dn).build();
        } else if (dn.isEmpty()) {
            response = (AuthenticationResponse)AuthenticationResponse.builder().response((AuthenticationHandlerResponse)((AuthenticationHandlerResponse.Builder)AuthenticationHandlerResponse.builder().diagnosticMessage("DN cannot be empty")).resultCode(AuthenticationResultCode.DN_RESOLUTION_FAILURE).build()).dn(dn).build();
        }
        return response;
    }

    protected AuthenticationRequest processRequest(String dn, AuthenticationRequest request) throws LdapException {
        if (this.returnAttributes == null && (this.getRequestHandlers() == null || this.getRequestHandlers().length == 0)) {
            return request;
        }
        AuthenticationRequest newRequest = AuthenticationRequest.copy(request);
        if (this.returnAttributes != null) {
            if (newRequest.getReturnAttributes() == null || ReturnAttributes.NONE.equalsAttributes(newRequest.getReturnAttributes())) {
                newRequest.setReturnAttributes(this.returnAttributes);
            } else {
                newRequest.setReturnAttributes(LdapUtils.concatArrays(newRequest.getReturnAttributes(), new String[][]{this.returnAttributes}));
            }
        }
        if (this.getRequestHandlers() != null && this.getRequestHandlers().length > 0) {
            for (AuthenticationRequestHandler ah : this.getRequestHandlers()) {
                ah.handle(dn, newRequest);
            }
        }
        return newRequest;
    }

    protected LdapEntry resolveEntry(AuthenticationCriteria criteria, AuthenticationHandlerResponse response) throws LdapException {
        LdapEntry entry = null;
        if (this.resolveEntryOnFailure || response.isSuccess()) {
            EntryResolver er = this.entryResolver != null ? this.entryResolver : (!ReturnAttributes.NONE.equalsAttributes(criteria.getAuthenticationRequest().getReturnAttributes()) ? (this.dnResolver instanceof AggregateDnResolver ? ((AggregateDnResolver)this.dnResolver).createEntryResolver(new SearchEntryResolver()) : new SearchEntryResolver()) : NOOP_RESOLVER);
            try {
                entry = er.resolve(criteria, response);
                this.logger.trace("resolved entry={} with resolver={}", (Object)entry, (Object)er);
            }
            catch (LdapException e) {
                this.logger.warn("entry resolution failed for resolver={}", (Object)er, (Object)e);
            }
        }
        if (entry == null) {
            entry = NOOP_RESOLVER.resolve(criteria, response);
            this.logger.trace("resolved entry={} with resolver={}", (Object)entry, (Object)NOOP_RESOLVER);
        }
        return entry;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "dnResolver=" + this.dnResolver + ", " + "authenticationHandler=" + this.authenticationHandler + ", " + "entryResolver=" + this.entryResolver + ", " + "returnAttributes=" + Arrays.toString(this.returnAttributes) + ", " + "requestHandlers=" + Arrays.toString(this.requestHandlers) + ", " + "responseHandlers=" + Arrays.toString(this.responseHandlers) + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Authenticator object = new Authenticator();

        protected Builder() {
        }

        public Builder dnResolver(DnResolver resolver2) {
            this.object.setDnResolver(resolver2);
            return this;
        }

        public Builder authenticationHandler(AuthenticationHandler handler) {
            this.object.setAuthenticationHandler(handler);
            return this;
        }

        public Builder entryResolver(EntryResolver resolver2) {
            this.object.setEntryResolver(resolver2);
            return this;
        }

        public Builder requestHandlers(AuthenticationRequestHandler ... handlers) {
            this.object.setRequestHandlers(handlers);
            return this;
        }

        public Builder responseHandlers(AuthenticationResponseHandler ... handlers) {
            this.object.setResponseHandlers(handlers);
            return this;
        }

        public Builder returnAttributes(String ... attributes) {
            this.object.setReturnAttributes(attributes);
            return this;
        }

        public Authenticator build() {
            return this.object;
        }
    }
}

