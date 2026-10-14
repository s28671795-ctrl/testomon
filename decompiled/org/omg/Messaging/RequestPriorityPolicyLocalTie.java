/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.Policy;
import org.omg.Messaging.PriorityRange;
import org.omg.Messaging.RequestPriorityPolicyOperations;
import org.omg.Messaging._RequestPriorityPolicyLocalBase;

public class RequestPriorityPolicyLocalTie
extends _RequestPriorityPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private RequestPriorityPolicyOperations _delegate;

    public RequestPriorityPolicyLocalTie(RequestPriorityPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public RequestPriorityPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(RequestPriorityPolicyOperations delegate) {
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
    public PriorityRange priority_range() {
        return this._delegate.priority_range();
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }
}

