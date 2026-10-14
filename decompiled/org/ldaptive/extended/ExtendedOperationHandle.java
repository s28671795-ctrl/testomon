/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.extended;

import org.ldaptive.LdapException;
import org.ldaptive.OperationHandle;
import org.ldaptive.extended.ExtendedRequest;
import org.ldaptive.extended.ExtendedResponse;
import org.ldaptive.handler.CompleteHandler;
import org.ldaptive.handler.ExceptionHandler;
import org.ldaptive.handler.ExtendedValueHandler;
import org.ldaptive.handler.IntermediateResponseHandler;
import org.ldaptive.handler.ReferralHandler;
import org.ldaptive.handler.ResponseControlHandler;
import org.ldaptive.handler.ResultHandler;
import org.ldaptive.handler.ResultPredicate;
import org.ldaptive.handler.UnsolicitedNotificationHandler;

public interface ExtendedOperationHandle
extends OperationHandle<ExtendedRequest, ExtendedResponse> {
    public ExtendedOperationHandle send();

    @Override
    public ExtendedResponse await() throws LdapException;

    @Override
    default public ExtendedResponse execute() throws LdapException {
        return this.send().await();
    }

    public ExtendedOperationHandle onResult(ResultHandler ... var1);

    public ExtendedOperationHandle onControl(ResponseControlHandler ... var1);

    public ExtendedOperationHandle onReferral(ReferralHandler ... var1);

    public ExtendedOperationHandle onIntermediate(IntermediateResponseHandler ... var1);

    public ExtendedOperationHandle onUnsolicitedNotification(UnsolicitedNotificationHandler ... var1);

    public ExtendedOperationHandle onException(ExceptionHandler var1);

    public ExtendedOperationHandle throwIf(ResultPredicate var1);

    public ExtendedOperationHandle onComplete(CompleteHandler var1);

    public ExtendedOperationHandle onExtended(ExtendedValueHandler ... var1);
}

