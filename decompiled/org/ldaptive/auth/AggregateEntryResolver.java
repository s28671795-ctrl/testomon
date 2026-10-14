/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.ResultCode;
import org.ldaptive.auth.AuthenticationCriteria;
import org.ldaptive.auth.AuthenticationHandlerResponse;
import org.ldaptive.auth.EntryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AggregateEntryResolver
implements EntryResolver {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<String, EntryResolver> entryResolvers = new HashMap<String, EntryResolver>();

    public AggregateEntryResolver() {
    }

    public AggregateEntryResolver(Map<String, EntryResolver> resolvers) {
        this.setEntryResolvers(resolvers);
    }

    public Map<String, EntryResolver> getEntryResolvers() {
        return Collections.unmodifiableMap(this.entryResolvers);
    }

    public void setEntryResolvers(Map<String, EntryResolver> resolvers) {
        this.logger.trace("setting entryResolvers: {}", (Object)resolvers);
        this.entryResolvers = resolvers;
    }

    public void addEntryResolver(String label, EntryResolver resolver2) {
        this.logger.trace("adding dnResolver: {}:{}", (Object)label, (Object)resolver2);
        this.entryResolvers.put(label, resolver2);
    }

    @Override
    public LdapEntry resolve(AuthenticationCriteria criteria, AuthenticationHandlerResponse response) throws LdapException {
        String[] labeledDn = criteria.getDn().split(":", 2);
        EntryResolver er = this.entryResolvers.get(labeledDn[0]);
        if (er == null) {
            throw new LdapException(ResultCode.PARAM_ERROR, "Could not find entry resolver for label: " + labeledDn[0]);
        }
        return er.resolve(new AuthenticationCriteria(labeledDn[1], criteria.getAuthenticationRequest()), response);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final AggregateEntryResolver object = new AggregateEntryResolver();

        protected Builder() {
        }

        public Builder resolver(String label, EntryResolver resolver2) {
            this.object.addEntryResolver(label, resolver2);
            return this;
        }

        public AggregateEntryResolver build() {
            return this.object;
        }
    }
}

