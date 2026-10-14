/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.referral;

import org.ldaptive.ConnectionFactory;
import org.ldaptive.LdapURL;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.filter.FilterParseException;
import org.ldaptive.filter.FilterParser;
import org.ldaptive.handler.SearchResultHandler;
import org.ldaptive.referral.AbstractFollowReferralHandler;
import org.ldaptive.referral.DefaultReferralConnectionFactory;
import org.ldaptive.referral.ReferralConnectionFactory;
import org.ldaptive.transport.DefaultSearchOperationHandle;

public class FollowSearchReferralHandler
extends AbstractFollowReferralHandler<SearchRequest, SearchResponse>
implements SearchResultHandler {
    public FollowSearchReferralHandler() {
        this(10, 1, new DefaultReferralConnectionFactory());
    }

    public FollowSearchReferralHandler(ReferralConnectionFactory factory2) {
        this(10, 1, factory2);
    }

    public FollowSearchReferralHandler(int limit) {
        this(limit, 1, new DefaultReferralConnectionFactory());
    }

    public FollowSearchReferralHandler(int limit, ReferralConnectionFactory factory2) {
        this(limit, 1, factory2);
    }

    private FollowSearchReferralHandler(int limit, int depth, ReferralConnectionFactory factory2) {
        super(limit, depth, factory2);
    }

    @Override
    protected SearchRequest createReferralRequest(LdapURL url2) {
        try {
            return (SearchRequest)((SearchRequest.Builder)SearchRequest.builder().controls(((SearchRequest)this.getRequest()).getControls())).scope(!url2.isDefaultScope() ? url2.getScope() : ((SearchRequest)this.getRequest()).getSearchScope()).dn(!url2.isDefaultBaseDn() ? url2.getBaseDn() : ((SearchRequest)this.getRequest()).getBaseDn()).filter(!url2.isDefaultFilter() ? FilterParser.parse(url2.getFilter()) : ((SearchRequest)this.getRequest()).getFilter()).sizeLimit(((SearchRequest)this.getRequest()).getSizeLimit()).timeLimit(((SearchRequest)this.getRequest()).getTimeLimit()).typesOnly(((SearchRequest)this.getRequest()).isTypesOnly()).returnAttributes(((SearchRequest)this.getRequest()).getReturnAttributes()).aliases(((SearchRequest)this.getRequest()).getDerefAliases()).binaryAttributes(((SearchRequest)this.getRequest()).getBinaryAttributes()).build();
        }
        catch (FilterParseException e) {
            throw new IllegalStateException("Could not parse filter in the LDAP URL '" + url2.getFilter() + "'", e);
        }
    }

    protected SearchOperation createReferralOperation(ConnectionFactory factory2) {
        DefaultSearchOperationHandle handle2 = (DefaultSearchOperationHandle)this.getHandle();
        SearchOperation op = new SearchOperation(factory2);
        op.setResultHandlers(handle2.getOnResult());
        op.setEntryHandlers(handle2.getOnEntry());
        op.setReferenceHandlers(handle2.getOnReference());
        op.setControlHandlers(handle2.getOnControl());
        op.setExceptionHandler(handle2.getOnException());
        op.setIntermediateResponseHandlers(handle2.getOnIntermediate());
        op.setReferralHandlers(handle2.getOnReferral());
        op.setUnsolicitedNotificationHandlers(handle2.getOnUnsolicitedNotification());
        op.setSearchResultHandlers(new FollowSearchReferralHandler(this.getReferralLimit(), this.getReferralDepth() + 1, this.getReferralConnectionFactory()));
        return op;
    }
}

