/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.CompareRequest;
import org.ldaptive.CompareResponse;
import org.ldaptive.LdapException;
import org.ldaptive.OperationHandle;
import org.ldaptive.handler.CompareValueHandler;
import org.ldaptive.handler.CompleteHandler;
import org.ldaptive.handler.ExceptionHandler;
import org.ldaptive.handler.IntermediateResponseHandler;
import org.ldaptive.handler.ReferralHandler;
import org.ldaptive.handler.ResponseControlHandler;
import org.ldaptive.handler.ResultHandler;
import org.ldaptive.handler.ResultPredicate;
import org.ldaptive.handler.UnsolicitedNotificationHandler;

public interface CompareOperationHandle
extends OperationHandle<CompareRequest, CompareResponse> {
    public CompareOperationHandle send();

    @Override
    public CompareResponse await() throws LdapException;

    @Override
    default public CompareResponse execute() throws LdapException {
        return this.send().await();
    }

    public CompareOperationHandle onResult(ResultHandler ... var1);

    public CompareOperationHandle onControl(ResponseControlHandler ... var1);

    public CompareOperationHandle onReferral(ReferralHandler ... var1);

    public CompareOperationHandle onIntermediate(IntermediateResponseHandler ... var1);

    public CompareOperationHandle onUnsolicitedNotification(UnsolicitedNotificationHandler ... var1);

    public CompareOperationHandle onException(ExceptionHandler var1);

    public CompareOperationHandle throwIf(ResultPredicate var1);

    public CompareOperationHandle onComplete(CompleteHandler var1);

    public CompareOperationHandle onCompare(CompareValueHandler ... var1);
}

