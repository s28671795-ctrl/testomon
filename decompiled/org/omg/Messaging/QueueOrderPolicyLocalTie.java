/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.Policy;
import org.omg.Messaging.QueueOrderPolicyOperations;
import org.omg.Messaging._QueueOrderPolicyLocalBase;

public class QueueOrderPolicyLocalTie
extends _QueueOrderPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private QueueOrderPolicyOperations _delegate;

    public QueueOrderPolicyLocalTie(QueueOrderPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public QueueOrderPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(QueueOrderPolicyOperations delegate) {
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
    public void destroy() {
        this._delegate.destroy();
    }

    @Override
    public short allowed_orders() {
        return this._delegate.allowed_orders();
    }
}

