/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.Policy;
import org.omg.Messaging.RebindPolicyOperations;
import org.omg.Messaging._RebindPolicyLocalBase;

public class RebindPolicyLocalTie
extends _RebindPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private RebindPolicyOperations _delegate;

    public RebindPolicyLocalTie(RebindPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public RebindPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(RebindPolicyOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public short rebind_mode() {
        return this._delegate.rebind_mode();
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
    public void destroy() {
        this._delegate.destroy();
    }
}

