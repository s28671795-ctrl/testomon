/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.time.Instant;
import org.ldaptive.LdapException;
import org.ldaptive.Request;
import org.ldaptive.Result;
import org.ldaptive.extended.ExtendedOperationHandle;
import org.ldaptive.handler.CompleteHandler;
import org.ldaptive.handler.ExceptionHandler;
import org.ldaptive.handler.IntermediateResponseHandler;
import org.ldaptive.handler.ReferralHandler;
import org.ldaptive.handler.ResponseControlHandler;
import org.ldaptive.handler.ResultHandler;
import org.ldaptive.handler.ResultPredicate;
import org.ldaptive.handler.UnsolicitedNotificationHandler;

public interface OperationHandle<Q extends Request, S extends Result> {
    public OperationHandle<Q, S> send();

    public S await() throws LdapException;

    default public S execute() throws LdapException {
        return this.send().await();
    }

    public OperationHandle<Q, S> onResult(ResultHandler ... var1);

    public OperationHandle<Q, S> onControl(ResponseControlHandler ... var1);

    public OperationHandle<Q, S> onReferral(ReferralHandler ... var1);

    public OperationHandle<Q, S> onIntermediate(IntermediateResponseHandler ... var1);

    public OperationHandle<Q, S> onUnsolicitedNotification(UnsolicitedNotificationHandler ... var1);

    public OperationHandle<Q, S> onException(ExceptionHandler var1);

    public OperationHandle<Q, S> onComplete(CompleteHandler var1);

    public OperationHandle<Q, S> throwIf(ResultPredicate var1);

    public void abandon();

    public ExtendedOperationHandle cancel();

    public Instant getSentTime();

    public Instant getReceivedTime();
}

