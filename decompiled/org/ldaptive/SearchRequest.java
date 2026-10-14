/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;
import org.ldaptive.AbstractRequestMessage;
import org.ldaptive.DerefAliases;
import org.ldaptive.FilterTemplate;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapUtils;
import org.ldaptive.ReturnAttributes;
import org.ldaptive.SearchScope;
import org.ldaptive.asn1.ApplicationDERTag;
import org.ldaptive.asn1.BooleanType;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.filter.Filter;
import org.ldaptive.filter.FilterParseException;
import org.ldaptive.filter.FilterParser;
import org.ldaptive.filter.PresenceFilter;

public class SearchRequest
extends AbstractRequestMessage {
    public static final int PROTOCOL_OP = 3;
    private static final int HASH_CODE_SEED = 307;
    private String baseDn = "";
    private SearchScope searchScope = SearchScope.SUBTREE;
    private DerefAliases derefAliases = DerefAliases.NEVER;
    private int sizeLimit;
    private Duration timeLimit = Duration.ZERO;
    private boolean typesOnly;
    private Filter searchFilter;
    private String[] returnAttributes = ReturnAttributes.ALL_USER.value();
    private String[] binaryAttributes;

    public SearchRequest() {
    }

    public SearchRequest(String dn) {
        this.setBaseDn(dn);
    }

    public SearchRequest(String dn, String filter2) {
        this.setBaseDn(dn);
        this.setFilter(filter2);
    }

    public SearchRequest(String dn, String filter2, String ... attributes) {
        this.setBaseDn(dn);
        this.setFilter(filter2);
        this.setReturnAttributes(attributes);
    }

    public SearchRequest(String dn, FilterTemplate template, String ... attributes) {
        this.setBaseDn(dn);
        this.setFilter(template);
        this.setReturnAttributes(attributes);
    }

    public SearchRequest(String dn, Filter filter2, String ... attributes) {
        this.setBaseDn(dn);
        this.setFilter(filter2);
        this.setReturnAttributes(attributes);
    }

    public SearchRequest(String dn, SearchScope scope, DerefAliases aliases, int size, Duration time, boolean types, Filter filter2, String ... attributes) {
        this.setBaseDn(dn);
        this.setSearchScope(scope);
        this.setDerefAliases(aliases);
        this.setSizeLimit(size);
        this.setTimeLimit(time);
        this.setTypesOnly(types);
        this.setFilter(filter2);
        this.setReturnAttributes(attributes);
    }

    public String getBaseDn() {
        return this.baseDn;
    }

    public void setBaseDn(String dn) {
        this.logger.trace("setting baseDn: {}", (Object)dn);
        this.baseDn = dn;
    }

    public SearchScope getSearchScope() {
        return this.searchScope;
    }

    public void setSearchScope(SearchScope scope) {
        if (scope == null) {
            throw new IllegalArgumentException("Scope cannot be null");
        }
        this.logger.trace("setting searchScope: {}", (Object)scope);
        this.searchScope = scope;
    }

    public DerefAliases getDerefAliases() {
        return this.derefAliases;
    }

    public void setDerefAliases(DerefAliases aliases) {
        if (aliases == null) {
            throw new IllegalArgumentException("Aliases cannot be null");
        }
        this.logger.trace("setting derefAliases: {}", (Object)aliases);
        this.derefAliases = aliases;
    }

    public int getSizeLimit() {
        return this.sizeLimit;
    }

    public void setSizeLimit(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Size limit cannot be negative");
        }
        this.logger.trace("setting sizeLimit: {}", (Object)limit);
        this.sizeLimit = limit;
    }

    public Duration getTimeLimit() {
        return this.timeLimit;
    }

    public void setTimeLimit(Duration limit) {
        if (limit == null || limit.isNegative()) {
            throw new IllegalArgumentException("Time limit cannot be null or negative");
        }
        this.logger.trace("setting timeLimit: {}", (Object)limit);
        this.timeLimit = limit;
    }

    public boolean isTypesOnly() {
        return this.typesOnly;
    }

    public void setTypesOnly(boolean types) {
        this.logger.trace("setting typesOnly: {}", (Object)types);
        this.typesOnly = types;
    }

    public Filter getFilter() {
        return this.searchFilter;
    }

    public void setFilter(Filter filter2) {
        this.logger.trace("setting filter: {}", (Object)filter2);
        this.searchFilter = filter2;
    }

    public void setFilter(String filter2) {
        this.logger.trace("setting filter: {}", (Object)filter2);
        try {
            this.searchFilter = FilterParser.parse(filter2);
        }
        catch (FilterParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setFilter(FilterTemplate template) {
        this.logger.trace("setting filter: {}", (Object)template);
        try {
            this.searchFilter = FilterParser.parse(template.format());
        }
        catch (FilterParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String[] getReturnAttributes() {
        return this.returnAttributes;
    }

    public void setReturnAttributes(String ... attributes) {
        this.logger.trace("setting returnAttributes: {}", (Object)Arrays.toString(attributes));
        this.returnAttributes = ReturnAttributes.parse(attributes);
    }

    public String[] getBinaryAttributes() {
        return this.binaryAttributes;
    }

    public void setBinaryAttributes(String ... attrs) {
        this.logger.trace("setting binaryAttributes: {}", (Object)Arrays.toString(attrs));
        this.binaryAttributes = attrs;
    }

    public void configureBinaryAttributes(LdapEntry entry) {
        if (this.binaryAttributes != null && this.binaryAttributes.length > 0) {
            for (LdapAttribute a : entry.getAttributes()) {
                a.configureBinary(this.binaryAttributes);
            }
        }
    }

    @Override
    protected DEREncoder[] getRequestEncoders(int id) {
        if (this.baseDn == null) {
            throw new NullPointerException("No baseDn defined in " + this);
        }
        if (this.searchFilter == null) {
            throw new NullPointerException("No search filter defined in " + this);
        }
        return new DEREncoder[]{new IntegerType(id), new ConstructedDEREncoder(new ApplicationDERTag(3, true), new OctetStringType(this.baseDn), new IntegerType((DERTag)UniversalDERTag.ENUM, this.searchScope.ordinal()), new IntegerType((DERTag)UniversalDERTag.ENUM, this.derefAliases.ordinal()), new IntegerType(this.sizeLimit), new IntegerType((int)this.timeLimit.getSeconds()), new BooleanType(this.typesOnly), this.searchFilter.getEncoder(), new ConstructedDEREncoder(UniversalDERTag.SEQ, (DEREncoder[])Stream.of(this.returnAttributes).map(OctetStringType::new).toArray(DEREncoder[]::new)))};
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SearchRequest) {
            SearchRequest v = (SearchRequest)o;
            return LdapUtils.areEqual(this.baseDn, v.baseDn) && LdapUtils.areEqual((Object)this.searchScope, (Object)v.searchScope) && LdapUtils.areEqual((Object)this.derefAliases, (Object)v.derefAliases) && LdapUtils.areEqual(this.sizeLimit, v.sizeLimit) && LdapUtils.areEqual(this.timeLimit, v.timeLimit) && LdapUtils.areEqual(this.typesOnly, v.typesOnly) && LdapUtils.areEqual(this.searchFilter, v.searchFilter) && LdapUtils.areEqual(this.returnAttributes, v.returnAttributes) && LdapUtils.areEqual(this.binaryAttributes, v.binaryAttributes) && LdapUtils.areEqual(this.getControls(), v.getControls());
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(307, new Object[]{this.baseDn, this.searchScope, this.derefAliases, this.sizeLimit, this.timeLimit, this.typesOnly, this.searchFilter, this.returnAttributes, this.binaryAttributes, this.getControls()});
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "dn=" + this.baseDn + ", " + "scope=" + (Object)((Object)this.searchScope) + ", " + "aliases=" + (Object)((Object)this.derefAliases) + ", " + "sizeLimit=" + this.sizeLimit + ", " + "timeLimit=" + this.timeLimit + ", " + "typesOnly=" + this.typesOnly + ", " + "filter=" + this.searchFilter + ", " + "returnAttributes=" + Arrays.toString(this.returnAttributes) + ", " + "binaryAttributes=" + Arrays.toString(this.binaryAttributes);
    }

    public static SearchRequest objectScopeSearchRequest(String dn) {
        return SearchRequest.objectScopeSearchRequest(dn, null);
    }

    public static SearchRequest objectScopeSearchRequest(String dn, String[] attrs) {
        return SearchRequest.objectScopeSearchRequest(dn, attrs, new PresenceFilter("objectClass"));
    }

    public static SearchRequest objectScopeSearchRequest(String dn, String[] attrs, String filter2) {
        try {
            return SearchRequest.objectScopeSearchRequest(dn, attrs, FilterParser.parse(filter2));
        }
        catch (FilterParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static SearchRequest objectScopeSearchRequest(String dn, String[] attrs, FilterTemplate template) {
        try {
            return SearchRequest.objectScopeSearchRequest(dn, attrs, FilterParser.parse(template.format()));
        }
        catch (FilterParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static SearchRequest objectScopeSearchRequest(String dn, String[] attrs, Filter filter2) {
        SearchRequest request = new SearchRequest();
        request.setBaseDn(dn);
        request.setFilter(filter2);
        request.setReturnAttributes(attrs);
        request.setSearchScope(SearchScope.OBJECT);
        return request;
    }

    public static SearchRequest copy(SearchRequest request) {
        SearchRequest sr = new SearchRequest();
        sr.setBaseDn(request.getBaseDn());
        sr.setSearchScope(request.getSearchScope());
        sr.setDerefAliases(request.getDerefAliases());
        sr.setSizeLimit(request.getSizeLimit());
        sr.setTimeLimit(request.getTimeLimit());
        sr.setTypesOnly(request.isTypesOnly());
        sr.setFilter(request.getFilter());
        sr.setReturnAttributes(request.getReturnAttributes());
        sr.setBinaryAttributes(request.getBinaryAttributes());
        sr.setControls(request.getControls());
        return sr;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(SearchRequest request) {
        return new Builder(request);
    }

    public static class Builder
    extends AbstractRequestMessage.AbstractBuilder<Builder, SearchRequest> {
        protected Builder() {
            super(new SearchRequest());
        }

        protected Builder(SearchRequest req) {
            super(req);
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder dn(String dn) {
            ((SearchRequest)this.object).setBaseDn(dn);
            return this.self();
        }

        public Builder scope(SearchScope scope) {
            ((SearchRequest)this.object).setSearchScope(scope);
            return this.self();
        }

        public Builder aliases(DerefAliases aliases) {
            ((SearchRequest)this.object).setDerefAliases(aliases);
            return this.self();
        }

        public Builder sizeLimit(int size) {
            ((SearchRequest)this.object).setSizeLimit(size);
            return this.self();
        }

        public Builder timeLimit(Duration time) {
            ((SearchRequest)this.object).setTimeLimit(time);
            return this.self();
        }

        public Builder typesOnly(boolean types) {
            ((SearchRequest)this.object).setTypesOnly(types);
            return this.self();
        }

        public Builder filter(Filter filter2) {
            ((SearchRequest)this.object).setFilter(filter2);
            return this.self();
        }

        public Builder filter(String filter2) {
            ((SearchRequest)this.object).setFilter(filter2);
            return this.self();
        }

        public Builder filter(FilterTemplate template) {
            ((SearchRequest)this.object).setFilter(template);
            return this.self();
        }

        public Builder returnAttributes(String ... attributes) {
            ((SearchRequest)this.object).setReturnAttributes(attributes);
            return this.self();
        }

        public Builder returnAttributes(Collection<String> attributes) {
            ((SearchRequest)this.object).setReturnAttributes((String[])attributes.toArray(String[]::new));
            return this.self();
        }

        public Builder binaryAttributes(String ... attributes) {
            ((SearchRequest)this.object).setBinaryAttributes(attributes);
            return this.self();
        }

        public Builder binaryAttributes(Collection<String> attributes) {
            ((SearchRequest)this.object).setBinaryAttributes((String[])attributes.toArray(String[]::new));
            return this.self();
        }
    }
}

