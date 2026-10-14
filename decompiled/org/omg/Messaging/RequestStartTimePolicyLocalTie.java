/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.Policy;
import org.omg.Messaging.RequestStartTimePolicyOperations;
import org.omg.Messaging._RequestStartTimePolicyLocalBase;
import org.omg.TimeBase.UtcT;

public class RequestStartTimePolicyLocalTie
extends _RequestStartTimePolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private RequestStartTimePolicyOperations _delegate;

    public RequestStartTimePolicyLocalTie(RequestStartTimePolicyOperations delegate) {
        this._delegate = delegate;
    }

    public RequestStartTimePolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(RequestStartTimePolicyOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public int policy_type() {
        return this._delegate.policy_type();
    }

    @Override
    public Policy copy() {
        return this._delegate.copy();
    }

    @Override
    public UtcT start_time() {
        return this._delegate.start_time();
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }
}

