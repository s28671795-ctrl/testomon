/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.Policy;
import org.omg.Messaging.SyncScopePolicyOperations;
import org.omg.Messaging._SyncScopePolicyLocalBase;

public class SyncScopePolicyLocalTie
extends _SyncScopePolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private SyncScopePolicyOperations _delegate;

    public SyncScopePolicyLocalTie(SyncScopePolicyOperations delegate) {
        this._delegate = delegate;
    }

    public SyncScopePolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(SyncScopePolicyOperations delegate) {
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
    public short synchronization() {
        return this._delegate.synchronization();
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }
}

