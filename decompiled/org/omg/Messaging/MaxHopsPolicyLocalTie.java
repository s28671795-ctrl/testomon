/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.Policy;
import org.omg.Messaging.MaxHopsPolicyOperations;
import org.omg.Messaging._MaxHopsPolicyLocalBase;

public class MaxHopsPolicyLocalTie
extends _MaxHopsPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private MaxHopsPolicyOperations _delegate;

    public MaxHopsPolicyLocalTie(MaxHopsPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public MaxHopsPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(MaxHopsPolicyOperations delegate) {
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
    public short max_hops() {
        return this._delegate.max_hops();
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }
}

