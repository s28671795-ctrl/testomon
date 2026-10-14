/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control.util;

import org.ldaptive.AbstractSearchOperationFactory;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.LdapException;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.control.SortKey;
import org.ldaptive.control.SortRequestControl;
import org.ldaptive.control.util.VirtualListViewParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VirtualListViewClient
extends AbstractSearchOperationFactory {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SortRequestControl sortControl;

    public VirtualListViewClient(ConnectionFactory cf, SortKey ... keys2) {
        this.setConnectionFactory(cf);
        this.sortControl = new SortRequestControl(keys2);
    }

    public SearchResponse execute(SearchRequest request, VirtualListViewParams params) throws LdapException {
        SearchOperation search = this.createSearchOperation();
        request.setControls(this.sortControl, params.createRequestControl(true));
        return search.execute(request);
    }

    public SearchResponse execute(SearchRequest request, VirtualListViewParams params, SearchResponse result) throws LdapException {
        SearchOperation search = this.createSearchOperation();
        request.setControls(this.sortControl, params.createRequestControl(result, true));
        return search.execute(request);
    }
}

