/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.Policy;
import org.omg.PortableServer.LifespanPolicyOperations;
import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer._LifespanPolicyLocalBase;

public class LifespanPolicyLocalTie
extends _LifespanPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private LifespanPolicyOperations _delegate;

    public LifespanPolicyLocalTie(LifespanPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public LifespanPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(LifespanPolicyOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public LifespanPolicyValue value() {
        return this._delegate.value();
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

