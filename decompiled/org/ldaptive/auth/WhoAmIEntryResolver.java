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
import org.ldaptive.extended.ExtendedResponse;
import org.ldaptive.extended.WhoAmIRequest;
import org.ldaptive.extended.WhoAmIResponseParser;

public class WhoAmIEntryResolver
extends AbstractSearchEntryResolver {
    @Override
    protected SearchResponse performLdapSearch(AuthenticationCriteria criteria, AuthenticationHandlerResponse response) throws LdapException {
        ExtendedResponse whoamiRes = response.getConnection().operation(new WhoAmIRequest()).execute();
        this.logger.debug("whoami operation returned {}", (Object)whoamiRes);
        if (!whoamiRes.isSuccess()) {
            throw new LdapException("Unsuccessful WhoAmI operation: " + whoamiRes);
        }
        String authzId = WhoAmIResponseParser.parse(whoamiRes);
        if (authzId == null || !authzId.contains(":")) {
            throw new IllegalStateException("WhoAmI operation returned illegal authorization ID: '" + authzId + "'");
        }
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

