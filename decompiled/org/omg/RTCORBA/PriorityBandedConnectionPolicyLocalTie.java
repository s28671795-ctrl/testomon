/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.Policy;
import org.omg.RTCORBA.PriorityBand;
import org.omg.RTCORBA.PriorityBandedConnectionPolicyOperations;
import org.omg.RTCORBA._PriorityBandedConnectionPolicyLocalBase;

public class PriorityBandedConnectionPolicyLocalTie
extends _PriorityBandedConnectionPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private PriorityBandedConnectionPolicyOperations _delegate;

    public PriorityBandedConnectionPolicyLocalTie(PriorityBandedConnectionPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public PriorityBandedConnectionPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(PriorityBandedConnectionPolicyOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public PriorityBand[] priority_bands() {
        return this._delegate.priority_bands();
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

