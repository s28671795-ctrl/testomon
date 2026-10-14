/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.Policy;
import org.omg.RTCORBA.PrivateConnectionPolicyOperations;
import org.omg.RTCORBA._PrivateConnectionPolicyLocalBase;

public class PrivateConnectionPolicyLocalTie
extends _PrivateConnectionPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private PrivateConnectionPolicyOperations _delegate;

    public PrivateConnectionPolicyLocalTie(PrivateConnectionPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public PrivateConnectionPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(PrivateConnectionPolicyOperations delegate) {
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
}

