/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.Policy;
import org.omg.RTCORBA.ClientProtocolPolicyOperations;
import org.omg.RTCORBA.Protocol;
import org.omg.RTCORBA._ClientProtocolPolicyLocalBase;

public class ClientProtocolPolicyLocalTie
extends _ClientProtocolPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private ClientProtocolPolicyOperations _delegate;

    public ClientProtocolPolicyLocalTie(ClientProtocolPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public ClientProtocolPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ClientProtocolPolicyOperations delegate) {
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
    public Protocol[] protocols() {
        return this._delegate.protocols();
    }
}

