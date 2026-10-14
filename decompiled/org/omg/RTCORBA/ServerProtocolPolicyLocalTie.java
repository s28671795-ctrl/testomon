/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.Policy;
import org.omg.RTCORBA.Protocol;
import org.omg.RTCORBA.ServerProtocolPolicyOperations;
import org.omg.RTCORBA._ServerProtocolPolicyLocalBase;

public class ServerProtocolPolicyLocalTie
extends _ServerProtocolPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private ServerProtocolPolicyOperations _delegate;

    public ServerProtocolPolicyLocalTie(ServerProtocolPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public ServerProtocolPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ServerProtocolPolicyOperations delegate) {
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

