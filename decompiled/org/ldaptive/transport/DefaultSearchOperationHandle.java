/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport;

import java.time.Duration;
import java.util.Arrays;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.SearchOperationHandle;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.SearchResultReference;
import org.ldaptive.handler.CompleteHandler;
import org.ldaptive.handler.ExceptionHandler;
import org.ldaptive.handler.IntermediateResponseHandler;
import org.ldaptive.handler.LdapEntryHandler;
import org.ldaptive.handler.ReferralHandler;
import org.ldaptive.handler.ResponseControlHandler;
import org.ldaptive.handler.ResultHandler;
import org.ldaptive.handler.ResultPredicate;
import org.ldaptive.handler.SearchReferenceHandler;
import org.ldaptive.handler.SearchResultHandler;
import org.ldaptive.handler.UnsolicitedNotificationHandler;
import org.ldaptive.transport.DefaultOperationHandle;
import org.ldaptive.transport.TransportConnection;

public class DefaultSearchOperationHandle
extends DefaultOperationHandle<SearchRequest, SearchResponse>
implements SearchOperationHandle {
    private static final boolean SORT_RESULTS = Boolean.valueOf(System.getProperty("org.ldaptive.sortSearchResults", "false"));
    private LdapEntryHandler[] onEntry;
    private SearchReferenceHandler[] onReference;
    private SearchResultHandler[] onSearchResult;
    private SearchResponse result = new SearchResponse();

    public DefaultSearchOperationHandle(SearchRequest req, TransportConnection conn, Duration timeout2) {
        super(req, conn, timeout2);
    }

    @Override
    public DefaultSearchOperationHandle send() {
        super.send();
        return this;
    }

    @Override
    public SearchResponse await() throws LdapException {
        SearchResponse done = (SearchResponse)super.await();
        this.result.initialize(done);
        if (SORT_RESULTS) {
            this.result = SearchResponse.sort(this.result);
        }
        if (this.onSearchResult != null) {
            for (SearchResultHandler func : this.onSearchResult) {
                try {
                    this.result = (SearchResponse)func.apply(this.result);
                }
                catch (Exception ex) {
                    this.logger.warn("Result function {} threw an exception", (Object)func, (Object)ex);
                }
            }
        }
        return this.result;
    }

    @Override
    public SearchResponse execute() throws LdapException {
        return this.send().await();
    }

    @Override
    public DefaultSearchOperationHandle onResult(ResultHandler ... function) {
        super.onResult(function);
        return this;
    }

    @Override
    public DefaultSearchOperationHandle onControl(ResponseControlHandler ... function) {
        super.onControl(function);
        return this;
    }

    @Override
    public DefaultSearchOperationHandle onReferral(ReferralHandler ... function) {
        super.onReferral(function);
        return this;
    }

    @Override
    public DefaultSearchOperationHandle onIntermediate(IntermediateResponseHandler ... function) {
        super.onIntermediate(function);
        return this;
    }

    @Override
    public DefaultSearchOperationHandle onUnsolicitedNotification(UnsolicitedNotificationHandler ... function) {
        super.onUnsolicitedNotification(function);
        return this;
    }

    @Override
    public DefaultSearchOperationHandle onException(ExceptionHandler function) {
        super.onException(function);
        return this;
    }

    @Override
    public DefaultSearchOperationHandle throwIf(ResultPredicate function) {
        super.throwIf(function);
        return this;
    }

    @Override
    public DefaultSearchOperationHandle onComplete(CompleteHandler function) {
        super.onComplete(function);
        return this;
    }

    @Override
    public DefaultSearchOperationHandle onEntry(LdapEntryHandler ... function) {
        this.onEntry = function;
        this.initializeMessageFunctional(this.onEntry);
        return this;
    }

    @Override
    public DefaultSearchOperationHandle onReference(SearchReferenceHandler ... function) {
        this.onReference = function;
        this.initializeMessageFunctional(this.onReference);
        return this;
    }

    @Override
    public DefaultSearchOperationHandle onSearchResult(SearchResultHandler ... function) {
        this.onSearchResult = function;
        this.initializeMessageFunctional(this.onSearchResult);
        return this;
    }

    public LdapEntryHandler[] getOnEntry() {
        return this.onEntry;
    }

    public SearchReferenceHandler[] getOnReference() {
        return this.onReference;
    }

    public SearchResultHandler[] getOnSearchResult() {
        return this.onSearchResult;
    }

    public void entry(LdapEntry r) {
        LdapEntry e = r;
        if (this.onEntry != null) {
            for (LdapEntryHandler func : this.onEntry) {
                try {
                    e = (LdapEntry)func.apply(e);
                }
                catch (Exception ex) {
                    this.logger.warn("Entry function {} in handle {} threw an exception", func, this, ex);
                }
            }
            this.consumedMessage();
        }
        if (e != null) {
            this.result.addEntries(e);
        }
    }

    public void reference(SearchResultReference r) {
        if (this.onReference != null) {
            for (SearchReferenceHandler func : this.onReference) {
                try {
                    func.accept(r.getUris());
                }
                catch (Exception ex) {
                    this.logger.warn("Reference consumer {} in handle {} threw an exception", func, this, ex);
                }
            }
            this.consumedMessage();
        }
        this.result.addReferences(r);
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "onEntry=" + Arrays.toString(this.onEntry) + ", " + "onReference=" + Arrays.toString(this.onReference) + ", " + "onSearchResult=" + Arrays.toString(this.onSearchResult);
    }
}

