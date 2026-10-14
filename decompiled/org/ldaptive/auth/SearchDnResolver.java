/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import java.util.Arrays;
import java.util.Iterator;
import org.ldaptive.AbstractSearchOperationFactory;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.DerefAliases;
import org.ldaptive.FilterTemplate;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.ReturnAttributes;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.SearchScope;
import org.ldaptive.auth.DnResolver;
import org.ldaptive.auth.User;

public class SearchDnResolver
extends AbstractSearchOperationFactory
implements DnResolver {
    private String baseDn = "";
    private String userFilter;
    private Object[] userFilterParameters;
    private boolean allowMultipleDns;
    private boolean subtreeSearch;
    private DerefAliases derefAliases = DerefAliases.NEVER;
    private String resolveFromAttribute;

    public SearchDnResolver() {
    }

    public SearchDnResolver(ConnectionFactory cf) {
        this.setConnectionFactory(cf);
    }

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

    public boolean getAllowMultipleDns() {
        return this.allowMultipleDns;
    }

    public void setAllowMultipleDns(boolean b) {
        this.logger.trace("setting allowMultipleDns: {}", (Object)b);
        this.allowMultipleDns = b;
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

    public String getResolveFromAttribute() {
        return this.resolveFromAttribute;
    }

    public void setResolveFromAttribute(String attributeName) {
        this.logger.trace("setting resolveFromAttribute: {}", (Object)attributeName);
        this.resolveFromAttribute = attributeName;
    }

    @Override
    public String resolve(User user) throws LdapException {
        this.logger.trace("resolve user={}", (Object)user);
        String dn = null;
        if (user != null) {
            FilterTemplate filter2 = this.createFilterTemplate(user);
            if (filter2 != null && filter2.getFilter() != null) {
                SearchResponse result = this.performLdapSearch(filter2);
                if (!result.isSuccess()) {
                    throw new LdapException("Error resolving DN for user " + user + " with filter " + filter2 + ". Unsuccessful search response: " + result);
                }
                Iterator<LdapEntry> answer = result.getEntries().iterator();
                if (answer != null && answer.hasNext()) {
                    dn = this.resolveDn(answer.next());
                    if (answer.hasNext()) {
                        this.logger.debug("Multiple results found for user={} using filter={}", (Object)user, (Object)filter2);
                        if (!this.allowMultipleDns) {
                            throw new LdapException("Found " + result.entrySize() + " DNs for " + user + " : " + result.getEntryDns());
                        }
                    }
                } else {
                    this.logger.info("Search for user={} failed using filter={}", (Object)user, (Object)filter2);
                }
            } else {
                this.logger.error("DN filter template not found, no search performed");
            }
        } else {
            this.logger.warn("DN resolution cannot occur, user is null");
        }
        this.logger.debug("Resolved dn={} for user={}", (Object)dn, (Object)user);
        return dn;
    }

    protected String resolveDn(LdapEntry entry) {
        if (this.resolveFromAttribute != null) {
            return this.performResolveFromAttribute(entry);
        }
        return entry.getDn();
    }

    protected String performResolveFromAttribute(LdapEntry entry) {
        LdapAttribute attr = entry.getAttribute(this.resolveFromAttribute);
        if (attr.size() != 1) {
            this.logger.warn("Skipping attribute as it does not meet cardinality (must contain a single value), in dn: {} resolveDnFromAttribute: {}", (Object)entry.getDn(), (Object)this.resolveFromAttribute);
            return null;
        }
        if (attr.isBinary()) {
            this.logger.warn("Skipping attribute as it is binary, in dn: {} resolveDnFromAttribute: {}", (Object)entry.getDn(), (Object)this.resolveFromAttribute);
            return null;
        }
        return attr.getStringValue();
    }

    protected FilterTemplate createFilterTemplate(User user) {
        FilterTemplate filter2 = new FilterTemplate();
        if (user != null && user.getIdentifier() != null && !"".equals(user.getIdentifier())) {
            if (this.userFilter != null) {
                this.logger.debug("Searching for DN using userFilter");
                filter2.setFilter(this.userFilter);
                if (this.userFilterParameters != null) {
                    filter2.setParameters(this.userFilterParameters);
                }
                filter2.setParameter("user", (Object)user.getIdentifier());
                filter2.setParameter("context", user.getContext());
            } else {
                this.logger.error("Invalid userFilter, cannot be null or empty.");
            }
        } else {
            this.logger.warn("Filter template cannot be created, user input was empty or null");
        }
        return filter2;
    }

    protected SearchRequest createSearchRequest(FilterTemplate template) {
        String[] stringArray;
        SearchRequest.Builder builder = SearchRequest.builder().dn(this.baseDn).filter(template);
        if (this.resolveFromAttribute == null) {
            stringArray = ReturnAttributes.NONE.value();
        } else {
            String[] stringArray2 = new String[1];
            stringArray = stringArray2;
            stringArray2[0] = this.resolveFromAttribute;
        }
        return (SearchRequest)builder.returnAttributes(stringArray).scope(this.subtreeSearch ? SearchScope.SUBTREE : SearchScope.ONELEVEL).aliases(this.derefAliases).build();
    }

    protected SearchResponse performLdapSearch(FilterTemplate template) throws LdapException {
        SearchRequest request = this.createSearchRequest(template);
        SearchOperation op = this.createSearchOperation();
        return op.execute(request);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "factory=" + this.getConnectionFactory() + ", " + "baseDn=" + this.baseDn + ", " + "userFilter=" + this.userFilter + ", " + "userFilterParameters=" + Arrays.toString(this.userFilterParameters) + ", " + "allowMultipleDns=" + this.allowMultipleDns + ", " + "subtreeSearch=" + this.subtreeSearch + ", " + "derefAliases=" + (Object)((Object)this.derefAliases) + ", " + "resolveDnFromAttribute=" + this.resolveFromAttribute + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final SearchDnResolver object = new SearchDnResolver();

        protected Builder() {
        }

        public Builder factory(ConnectionFactory factory2) {
            this.object.setConnectionFactory(factory2);
            return this;
        }

        public Builder dn(String dn) {
            this.object.setBaseDn(dn);
            return this;
        }

        public Builder filter(String filter2) {
            this.object.setUserFilter(filter2);
            return this;
        }

        public Builder filterParameters(Object ... params) {
            this.object.setUserFilterParameters(params);
            return this;
        }

        public Builder allowMultipleDns(boolean multipleDns) {
            this.object.setAllowMultipleDns(multipleDns);
            return this;
        }

        public Builder subtreeSearch(boolean b) {
            this.object.setSubtreeSearch(b);
            return this;
        }

        public Builder aliases(DerefAliases aliases) {
            this.object.setDerefAliases(aliases);
            return this;
        }

        public Builder resolveFromAttribute(String attributeName) {
            this.object.setResolveFromAttribute(attributeName);
            return this;
        }

        public SearchDnResolver build() {
            return this.object;
        }
    }
}

