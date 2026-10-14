/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.Policy;
import org.omg.Messaging.RoutingPolicyOperations;
import org.omg.Messaging.RoutingTypeRange;
import org.omg.Messaging._RoutingPolicyLocalBase;

public class RoutingPolicyLocalTie
extends _RoutingPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private RoutingPolicyOperations _delegate;

    public RoutingPolicyLocalTie(RoutingPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public RoutingPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(RoutingPolicyOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public RoutingTypeRange routing_range() {
        return this._delegate.routing_range();
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

