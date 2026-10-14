/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.LdapException;
import org.ldaptive.OperationHandle;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
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

public interface SearchOperationHandle
extends OperationHandle<SearchRequest, SearchResponse> {
    public SearchOperationHandle send();

    @Override
    public SearchResponse await() throws LdapException;

    @Override
    default public SearchResponse execute() throws LdapException {
        return this.send().await();
    }

    public SearchOperationHandle onResult(ResultHandler ... var1);

    public SearchOperationHandle onControl(ResponseControlHandler ... var1);

    public SearchOperationHandle onReferral(ReferralHandler ... var1);

    public SearchOperationHandle onIntermediate(IntermediateResponseHandler ... var1);

    public SearchOperationHandle onUnsolicitedNotification(UnsolicitedNotificationHandler ... var1);

    public SearchOperationHandle onException(ExceptionHandler var1);

    public SearchOperationHandle onComplete(CompleteHandler var1);

    public SearchOperationHandle throwIf(ResultPredicate var1);

    public SearchOperationHandle onEntry(LdapEntryHandler ... var1);

    public SearchOperationHandle onReference(SearchReferenceHandler ... var1);

    public SearchOperationHandle onSearchResult(SearchResultHandler ... var1);
}

