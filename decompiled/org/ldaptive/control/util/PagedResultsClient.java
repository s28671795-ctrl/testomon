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
import org.ldaptive.control.PagedResultsControl;
import org.ldaptive.control.util.CookieManager;
import org.ldaptive.control.util.DefaultCookieManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PagedResultsClient
extends AbstractSearchOperationFactory {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int resultSize;

    public PagedResultsClient(ConnectionFactory cf, int size) {
        this.setConnectionFactory(cf);
        this.resultSize = size;
    }

    public SearchResponse execute(SearchRequest request) throws LdapException {
        return this.execute(request, new DefaultCookieManager());
    }

    public SearchResponse execute(SearchRequest request, SearchResponse result) throws LdapException {
        byte[] cookie = this.getPagedResultsCookie(result);
        if (cookie == null) {
            throw new IllegalArgumentException("Response does not contain a paged results cookie");
        }
        return this.execute(request, new DefaultCookieManager(cookie));
    }

    public SearchResponse execute(SearchRequest request, CookieManager manager) throws LdapException {
        SearchOperation search = this.createSearchOperation();
        request.setControls(new PagedResultsControl(this.resultSize, manager.readCookie(), true));
        SearchResponse result = search.execute(request);
        byte[] cookie = this.getPagedResultsCookie(result);
        if (cookie != null) {
            manager.writeCookie(cookie);
        }
        return result;
    }

    public boolean hasMore(SearchResponse result) {
        return this.getPagedResultsCookie(result) != null;
    }

    public SearchResponse executeToCompletion(SearchRequest request) throws LdapException {
        return this.executeToCompletion(request, new DefaultCookieManager());
    }

    public SearchResponse executeToCompletion(SearchRequest request, CookieManager manager) throws LdapException {
        SearchResponse result = null;
        SearchResponse combinedResults = new SearchResponse();
        SearchOperation search = this.createSearchOperation();
        byte[] cookie = manager.readCookie();
        do {
            if (result != null) {
                combinedResults.addEntries(result.getEntries());
                combinedResults.addReferences(result.getReferences());
            }
            request.setControls(new PagedResultsControl(this.resultSize, cookie, true));
            result = search.execute(request);
            cookie = this.getPagedResultsCookie(result);
            if (cookie == null) continue;
            manager.writeCookie(cookie);
        } while (cookie != null);
        result.addEntries(combinedResults.getEntries());
        result.addReferences(combinedResults.getReferences());
        return result;
    }

    protected byte[] getPagedResultsCookie(SearchResponse result) {
        byte[] cookie = null;
        PagedResultsControl ctl = (PagedResultsControl)result.getControl("1.2.840.113556.1.4.319");
        if (ctl != null && ctl.getCookie() != null && ctl.getCookie().length > 0) {
            cookie = ctl.getCookie();
        }
        return cookie;
    }
}

