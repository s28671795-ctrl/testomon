/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import org.ldaptive.LdapException;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.auth.AbstractSearchEntryResolver;
import org.ldaptive.auth.AuthenticationCriteria;
import org.ldaptive.auth.AuthenticationHandlerResponse;
import org.ldaptive.control.AuthorizationIdentityResponseControl;

public class AuthorizationIdentityEntryResolver
extends AbstractSearchEntryResolver {
    @Override
    protected SearchResponse performLdapSearch(AuthenticationCriteria criteria, AuthenticationHandlerResponse response) throws LdapException {
        AuthorizationIdentityResponseControl ctrl = (AuthorizationIdentityResponseControl)response.getControl("2.16.840.1.113730.3.4.15");
        if (ctrl == null) {
            throw new IllegalStateException("Authorization Identity Response Control not found");
        }
        this.logger.debug("Found authorization identity response control {}", (Object)ctrl);
        String authzId = ctrl.getAuthorizationId();
        String dn = authzId.split(":", 2)[1].trim();
        return response.getConnection().operation(this.createSearchRequest(criteria, dn)).execute();
    }

    protected SearchRequest createSearchRequest(AuthenticationCriteria ac, String dn) {
        SearchRequest request = SearchRequest.objectScopeSearchRequest(dn, ac.getAuthenticationRequest().getReturnAttributes());
        request.setDerefAliases(this.getDerefAliases());
        request.setBinaryAttributes(this.getBinaryAttributes());
        return request;
    }
}

