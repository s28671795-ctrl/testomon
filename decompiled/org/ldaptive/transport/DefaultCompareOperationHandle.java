/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport;

import java.time.Duration;
import java.util.Arrays;
import org.ldaptive.CompareOperationHandle;
import org.ldaptive.CompareRequest;
import org.ldaptive.CompareResponse;
import org.ldaptive.LdapException;
import org.ldaptive.ResultCode;
import org.ldaptive.handler.CompareValueHandler;
import org.ldaptive.handler.CompleteHandler;
import org.ldaptive.handler.ExceptionHandler;
import org.ldaptive.handler.IntermediateResponseHandler;
import org.ldaptive.handler.ReferralHandler;
import org.ldaptive.handler.ResponseControlHandler;
import org.ldaptive.handler.ResultHandler;
import org.ldaptive.handler.ResultPredicate;
import org.ldaptive.handler.UnsolicitedNotificationHandler;
import org.ldaptive.transport.DefaultOperationHandle;
import org.ldaptive.transport.TransportConnection;

public class DefaultCompareOperationHandle
extends DefaultOperationHandle<CompareRequest, CompareResponse>
implements CompareOperationHandle {
    private CompareValueHandler[] onCompare;

    public DefaultCompareOperationHandle(CompareRequest req, TransportConnection conn, Duration timeout2) {
        super(req, conn, timeout2);
    }

    @Override
    public DefaultCompareOperationHandle send() {
        super.send();
        return this;
    }

    @Override
    public CompareResponse await() throws LdapException {
        return (CompareResponse)super.await();
    }

    @Override
    public DefaultCompareOperationHandle onResult(ResultHandler ... function) {
        super.onResult(function);
        return this;
    }

    @Override
    public DefaultCompareOperationHandle onControl(ResponseControlHandler ... function) {
        super.onControl(function);
        return this;
    }

    @Override
    public DefaultCompareOperationHandle onReferral(ReferralHandler ... function) {
        super.onReferral(function);
        return this;
    }

    @Override
    public DefaultCompareOperationHandle onIntermediate(IntermediateResponseHandler ... function) {
        super.onIntermediate(function);
        return this;
    }

    @Override
    public DefaultCompareOperationHandle onUnsolicitedNotification(UnsolicitedNotificationHandler ... function) {
        super.onUnsolicitedNotification(function);
        return this;
    }

    @Override
    public DefaultCompareOperationHandle onException(ExceptionHandler function) {
        super.onException(function);
        return this;
    }

    @Override
    public DefaultCompareOperationHandle throwIf(ResultPredicate function) {
        super.throwIf(function);
        return this;
    }

    @Override
    public DefaultCompareOperationHandle onComplete(CompleteHandler function) {
        super.onComplete(function);
        return this;
    }

    @Override
    public DefaultCompareOperationHandle onCompare(CompareValueHandler ... function) {
        this.onCompare = function;
        this.initializeMessageFunctional(this.onCompare);
        return this;
    }

    public void compare(CompareResponse response) {
        if (this.onCompare != null) {
            for (CompareValueHandler func : this.onCompare) {
                try {
                    if (response.getResultCode() == ResultCode.COMPARE_TRUE) {
                        func.accept(Boolean.TRUE);
                        continue;
                    }
                    if (response.getResultCode() != ResultCode.COMPARE_FALSE) continue;
                    func.accept(Boolean.FALSE);
                }
                catch (Exception ex) {
                    this.logger.warn("Compare response consumer {} threw an exception", (Object)func, (Object)ex);
                }
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "onCompare=" + Arrays.toString(this.onCompare);
    }
}

