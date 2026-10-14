/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.Policy;
import org.omg.Messaging.RelativeRequestTimeoutPolicyOperations;
import org.omg.Messaging._RelativeRequestTimeoutPolicyLocalBase;

public class RelativeRequestTimeoutPolicyLocalTie
extends _RelativeRequestTimeoutPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private RelativeRequestTimeoutPolicyOperations _delegate;

    public RelativeRequestTimeoutPolicyLocalTie(RelativeRequestTimeoutPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public RelativeRequestTimeoutPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(RelativeRequestTimeoutPolicyOperations delegate) {
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

