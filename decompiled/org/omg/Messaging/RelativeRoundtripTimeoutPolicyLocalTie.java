/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.Policy;
import org.omg.Messaging.RelativeRoundtripTimeoutPolicyOperations;
import org.omg.Messaging._RelativeRoundtripTimeoutPolicyLocalBase;

public class RelativeRoundtripTimeoutPolicyLocalTie
extends _RelativeRoundtripTimeoutPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private RelativeRoundtripTimeoutPolicyOperations _delegate;

    public RelativeRoundtripTimeoutPolicyLocalTie(RelativeRoundtripTimeoutPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public RelativeRoundtripTimeoutPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(RelativeRoundtripTimeoutPolicyOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public int policy_type() {
        return this._delegate.policy_type();
    }

    @Override
    public long relative_expiry() {
        return this._delegate.relative_expiry();
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

