/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.Policy;
import org.omg.Messaging.ReplyEndTimePolicyOperations;
import org.omg.Messaging._ReplyEndTimePolicyLocalBase;
import org.omg.TimeBase.UtcT;

public class ReplyEndTimePolicyLocalTie
extends _ReplyEndTimePolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private ReplyEndTimePolicyOperations _delegate;

    public ReplyEndTimePolicyLocalTie(ReplyEndTimePolicyOperations delegate) {
        this._delegate = delegate;
    }

    public ReplyEndTimePolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ReplyEndTimePolicyOperations delegate) {
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
    public UtcT end_time() {
        return this._delegate.end_time();
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }
}

