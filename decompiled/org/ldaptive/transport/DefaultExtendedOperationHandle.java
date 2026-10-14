/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport;

import java.time.Duration;
import java.util.Arrays;
import org.ldaptive.LdapException;
import org.ldaptive.extended.ExtendedOperationHandle;
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
import org.ldaptive.transport.DefaultOperationHandle;
import org.ldaptive.transport.TransportConnection;

public class DefaultExtendedOperationHandle
extends DefaultOperationHandle<ExtendedRequest, ExtendedResponse>
implements ExtendedOperationHandle {
    private ExtendedValueHandler[] onExtended;

    public DefaultExtendedOperationHandle(ExtendedRequest req, TransportConnection conn, Duration timeout2) {
        super(req, conn, timeout2);
    }

    @Override
    public DefaultExtendedOperationHandle send() {
        super.send();
        return this;
    }

    @Override
    public ExtendedResponse await() throws LdapException {
        return (ExtendedResponse)super.await();
    }

    @Override
    public DefaultExtendedOperationHandle onResult(ResultHandler ... function) {
        super.onResult(function);
        return this;
    }

    @Override
    public DefaultExtendedOperationHandle onControl(ResponseControlHandler ... function) {
        super.onControl(function);
        return this;
    }

    @Override
    public DefaultExtendedOperationHandle onReferral(ReferralHandler ... function) {
        super.onReferral(function);
        return this;
    }

    @Override
    public DefaultExtendedOperationHandle onIntermediate(IntermediateResponseHandler ... function) {
        super.onIntermediate(function);
        return this;
    }

    @Override
    public DefaultExtendedOperationHandle onUnsolicitedNotification(UnsolicitedNotificationHandler ... function) {
        super.onUnsolicitedNotification(function);
        return this;
    }

    @Override
    public DefaultExtendedOperationHandle onException(ExceptionHandler function) {
        super.onException(function);
        return this;
    }

    @Override
    public DefaultExtendedOperationHandle throwIf(ResultPredicate function) {
        super.throwIf(function);
        return this;
    }

    @Override
    public DefaultExtendedOperationHandle onComplete(CompleteHandler function) {
        super.onComplete(function);
        return this;
    }

    @Override
    public DefaultExtendedOperationHandle onExtended(ExtendedValueHandler ... function) {
        this.onExtended = function;
        this.initializeMessageFunctional(this.onExtended);
        return this;
    }

    public void extended(ExtendedResponse response) {
        if (this.onExtended != null) {
            for (ExtendedValueHandler func : this.onExtended) {
                try {
                    func.accept(response.getResponseName(), response.getResponseValue());
                }
                catch (Exception ex) {
                    this.logger.warn("Extended response biconsumer {} threw an exception", (Object)func, (Object)ex);
                }
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "onExtended=" + Arrays.toString(this.onExtended);
    }
}

