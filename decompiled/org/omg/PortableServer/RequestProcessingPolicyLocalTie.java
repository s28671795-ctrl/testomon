/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.Policy;
import org.omg.PortableServer.RequestProcessingPolicyOperations;
import org.omg.PortableServer.RequestProcessingPolicyValue;
import org.omg.PortableServer._RequestProcessingPolicyLocalBase;

public class RequestProcessingPolicyLocalTie
extends _RequestProcessingPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private RequestProcessingPolicyOperations _delegate;

    public RequestProcessingPolicyLocalTie(RequestProcessingPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public RequestProcessingPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(RequestProcessingPolicyOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public RequestProcessingPolicyValue value() {
        return this._delegate.value();
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

