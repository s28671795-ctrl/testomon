/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import java.util.Arrays;
import java.util.Iterator;
import org.ldaptive.AbstractSearchOperationFactory;
import org.ldaptive.DerefAliases;
import org.ldaptive.FilterTemplate;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.SearchScope;
import org.ldaptive.auth.AuthenticationCriteria;
import org.ldaptive.auth.AuthenticationHandlerResponse;
import org.ldaptive.auth.EntryResolver;

public abstract class AbstractSearchEntryResolver
extends AbstractSearchOperationFactory
implements EntryResolver {
    private String baseDn = "";
    private String userFilter;
    private Object[] userFilterParameters;
    private boolean allowMultipleEntries;
    private boolean subtreeSearch;
    private DerefAliases derefAliases = DerefAliases.NEVER;
    private String[] binaryAttributes;

    public String getBaseDn() {
        return this.baseDn;
    }

    public void setBaseDn(String dn) {
        this.logger.trace("setting baseDn: {}", (Object)dn);
        this.baseDn = dn;
    }

    public String getUserFilter() {
        return this.userFilter;
    }

    public void setUserFilter(String filter2) {
        this.logger.trace("setting userFilter: {}", (Object)filter2);
        this.userFilter = filter2;
    }

    public Object[] getUserFilterParameters() {
        return this.userFilterParameters;
    }

    public void setUserFilterParameters(Object[] filterParams) {
        this.logger.trace("setting userFilterParameters: {}", (Object)Arrays.toString(filterParams));
        this.userFilterParameters = filterParams;
    }

    public boolean getAllowMultipleEntries() {
        return this.allowMultipleEntries;
    }

    public void setAllowMultipleEntries(boolean b) {
        this.logger.trace("setting allowMultipleEntries: {}", (Object)b);
        this.allowMultipleEntries = b;
    }

    public boolean getSubtreeSearch() {
        return this.subtreeSearch;
    }

    public void setSubtreeSearch(boolean b) {
        this.logger.trace("setting subtreeSearch: {}", (Object)b);
        this.subtreeSearch = b;
    }

    public DerefAliases getDerefAliases() {
        return this.derefAliases;
    }

    public void setDerefAliases(DerefAliases da) {
        this.logger.trace("setting derefAliases: {}", (Object)da);
        this.derefAliases = da;
    }

    public String[] getBinaryAttributes() {
        return this.binaryAttributes;
    }

    public void setBinaryAttributes(String ... attrs) {
        this.logger.trace("setting binaryAttributes: {}", (Object)Arrays.toString(attrs));
        this.binaryAttributes = attrs;
    }

    protected abstract SearchResponse performLdapSearch(AuthenticationCriteria var1, AuthenticationHandlerResponse var2) throws LdapException;

    protected FilterTemplate createFilterTemplate(AuthenticationCriteria ac) {
        FilterTemplate filter2 = new FilterTemplate();
        if (this.userFilter != null) {
            this.logger.debug("Searching for entry using userFilter");
            filter2.setFilter(this.userFilter);
            if (this.userFilterParameters != null) {
                filter2.setParameters(this.userFilterParameters);
            }
            filter2.setParameter("user", (Object)ac.getAuthenticationRequest().getUser().getIdentifier());
            filter2.setParameter("context", ac.getAuthenticationRequest().getUser().getContext());
            filter2.setParameter("dn", (Object)ac.getDn());
        } else {
            this.logger.error("Invalid userFilter, cannot be null.");
        }
        return filter2;
    }

    protected SearchRequest createSearchRequest(AuthenticationCriteria ac) {
        SearchRequest request = this.userFilter != null ? (SearchRequest)SearchRequest.builder().dn(this.baseDn).filter(this.createFilterTemplate(ac)).returnAttributes(ac.getAuthenticationRequest().getReturnAttributes()).scope(this.subtreeSearch ? SearchScope.SUBTREE : SearchScope.ONELEVEL).build() : SearchRequest.objectScopeSearchRequest(ac.getDn(), ac.getAuthenticationRequest().getReturnAttributes());
        request.setDerefAliases(this.derefAliases);
        request.setBinaryAttributes(this.binaryAttributes);
        return request;
    }

    @Override
    public LdapEntry resolve(AuthenticationCriteria criteria, AuthenticationHandlerResponse response) throws LdapException {
        this.logger.trace("resolve criteria={}", (Object)criteria);
        SearchResponse result = this.performLdapSearch(criteria, response);
        this.logger.debug("Resolved result={} for criteria={}", (Object)result, (Object)criteria);
        if (!result.isSuccess()) {
            throw new LdapException("Error resolving entry for " + criteria.getDn() + ". Unsuccessful search response: " + result);
        }
        LdapEntry entry = null;
        Iterator<LdapEntry> answer = result.getEntries().iterator();
        if (answer != null && answer.hasNext()) {
            entry = answer.next();
            if (answer.hasNext()) {
                this.logger.debug("Multiple results found for user={}", (Object)criteria.getDn());
                if (!this.allowMultipleEntries) {
                    throw new LdapException("Found more than (1) entry for: " + criteria.getDn());
                }
            }
        }
        return entry;
    }
}

