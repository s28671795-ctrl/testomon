/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.control.util;

import org.ldaptive.ConnectionFactory;
import org.ldaptive.LdapException;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.ad.control.DirSyncControl;
import org.ldaptive.ad.control.ExtendedDnControl;
import org.ldaptive.ad.control.ShowDeletedControl;
import org.ldaptive.control.RequestControl;
import org.ldaptive.control.util.CookieManager;
import org.ldaptive.control.util.DefaultCookieManager;
import org.ldaptive.handler.ExceptionHandler;
import org.ldaptive.handler.LdapEntryHandler;
import org.ldaptive.handler.ResultHandler;
import org.ldaptive.handler.ResultPredicate;
import org.ldaptive.handler.SearchReferenceHandler;
import org.ldaptive.handler.SearchResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirSyncClient {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ConnectionFactory factory;
    private final DirSyncControl.Flag[] dirSyncFlags;
    private final int maxAttributeCount;
    private ExtendedDnControl.Flag extendedDnFlag = ExtendedDnControl.Flag.STANDARD;
    private ResultHandler[] resultHandlers;
    private ExceptionHandler exceptionHandler;
    private ResultPredicate throwCondition;
    private LdapEntryHandler[] entryHandlers;
    private SearchReferenceHandler[] referenceHandlers;
    private SearchResultHandler[] searchResultHandlers;

    public DirSyncClient(ConnectionFactory cf) {
        this(cf, null, 0);
    }

    public DirSyncClient(ConnectionFactory cf, DirSyncControl.Flag[] dsFlags) {
        this(cf, dsFlags, 0);
    }

    public DirSyncClient(ConnectionFactory cf, DirSyncControl.Flag[] dsFlags, int count) {
        this.factory = cf;
        this.dirSyncFlags = dsFlags;
        this.maxAttributeCount = count;
    }

    public ResultHandler[] getResultHandlers() {
        return this.resultHandlers;
    }

    public void setResultHandlers(ResultHandler ... handlers) {
        this.resultHandlers = handlers;
    }

    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    public void setExceptionHandler(ExceptionHandler handler) {
        this.exceptionHandler = handler;
    }

    public ResultPredicate getThrowCondition() {
        return this.throwCondition;
    }

    public void setThrowCondition(ResultPredicate function) {
        this.throwCondition = function;
    }

    public LdapEntryHandler[] getEntryHandlers() {
        return this.entryHandlers;
    }

    public void setEntryHandlers(LdapEntryHandler ... handlers) {
        this.entryHandlers = handlers;
    }

    public SearchReferenceHandler[] getReferenceHandlers() {
        return this.referenceHandlers;
    }

    public void setReferenceHandlers(SearchReferenceHandler ... handlers) {
        this.referenceHandlers = handlers;
    }

    public SearchResultHandler[] getSearchResultHandlers() {
        return this.searchResultHandlers;
    }

    public void setSearchResultHandlers(SearchResultHandler ... handlers) {
        this.searchResultHandlers = handlers;
    }

    public ExtendedDnControl.Flag getExtendedDnFlag() {
        return this.extendedDnFlag;
    }

    public void setExtendedDnFlag(ExtendedDnControl.Flag flag) {
        this.extendedDnFlag = flag;
    }

    public SearchResponse execute(SearchRequest request) throws LdapException {
        return this.execute(request, new DefaultCookieManager());
    }

    public SearchResponse execute(SearchRequest request, SearchResponse result) throws LdapException {
        byte[] cookie = this.getDirSyncCookie(result);
        if (cookie == null) {
            throw new IllegalArgumentException("Response does not contain a dir sync cookie");
        }
        return this.execute(request, new DefaultCookieManager(cookie));
    }

    public SearchResponse execute(SearchRequest request, CookieManager manager) throws LdapException {
        request.setControls(this.createRequestControls(manager.readCookie()));
        SearchOperation search = this.createSearchOperation();
        SearchResponse result = search.execute(request);
        byte[] cookie = this.getDirSyncCookie(result);
        if (cookie != null) {
            manager.writeCookie(cookie);
        }
        return result;
    }

    public boolean hasMore(SearchResponse result) {
        return this.getDirSyncFlags(result) != 0L;
    }

    public SearchResponse executeToCompletion(SearchRequest request) throws LdapException {
        return this.executeToCompletion(request, new DefaultCookieManager());
    }

    public SearchResponse executeToCompletion(SearchRequest request, CookieManager manager) throws LdapException {
        long flags;
        SearchResponse response = null;
        SearchResponse combinedResponse = new SearchResponse();
        SearchOperation search = this.createSearchOperation();
        byte[] cookie = manager.readCookie();
        do {
            if (response != null) {
                combinedResponse.addEntries(response.getEntries());
                combinedResponse.addReferences(response.getReferences());
            }
            request.setControls(this.createRequestControls(cookie));
            response = search.execute(request);
            flags = this.getDirSyncFlags(response);
            cookie = this.getDirSyncCookie(response);
            if (cookie == null) continue;
            manager.writeCookie(cookie);
        } while (flags != 0L);
        response.addEntries(combinedResponse.getEntries());
        response.addReferences(combinedResponse.getReferences());
        return response;
    }

    protected SearchOperation createSearchOperation() {
        SearchOperation search = new SearchOperation(this.factory);
        search.setResultHandlers(this.resultHandlers);
        search.setExceptionHandler(this.exceptionHandler);
        search.setThrowCondition(this.throwCondition);
        search.setEntryHandlers(this.entryHandlers);
        search.setReferenceHandlers(this.referenceHandlers);
        search.setSearchResultHandlers(this.searchResultHandlers);
        return search;
    }

    protected long getDirSyncFlags(SearchResponse result) {
        long flags = -1L;
        DirSyncControl ctl = (DirSyncControl)result.getControl("1.2.840.113556.1.4.841");
        if (ctl != null) {
            flags = ctl.getFlags();
        }
        return flags;
    }

    protected byte[] getDirSyncCookie(SearchResponse result) {
        byte[] cookie = null;
        DirSyncControl ctl = (DirSyncControl)result.getControl("1.2.840.113556.1.4.841");
        if (ctl != null && ctl.getCookie() != null && ctl.getCookie().length > 0) {
            cookie = ctl.getCookie();
        }
        return cookie;
    }

    private RequestControl[] createRequestControls(byte[] cookie) {
        return new RequestControl[]{new DirSyncControl(this.dirSyncFlags, cookie, this.maxAttributeCount, true), new ExtendedDnControl(this.extendedDnFlag), new ShowDeletedControl()};
    }
}

