/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.Policy;
import org.omg.RTCORBA.PriorityModel;
import org.omg.RTCORBA.PriorityModelPolicyOperations;
import org.omg.RTCORBA._PriorityModelPolicyLocalBase;

public class PriorityModelPolicyLocalTie
extends _PriorityModelPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private PriorityModelPolicyOperations _delegate;

    public PriorityModelPolicyLocalTie(PriorityModelPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public PriorityModelPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(PriorityModelPolicyOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public short server_priority() {
        return this._delegate.server_priority();
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
    public PriorityModel priority_model() {
        return this._delegate.priority_model();
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }
}

