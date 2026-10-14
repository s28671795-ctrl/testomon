/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.Policy;
import org.omg.PortableServer.ImplicitActivationPolicyOperations;
import org.omg.PortableServer.ImplicitActivationPolicyValue;
import org.omg.PortableServer._ImplicitActivationPolicyLocalBase;

public class ImplicitActivationPolicyLocalTie
extends _ImplicitActivationPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private ImplicitActivationPolicyOperations _delegate;

    public ImplicitActivationPolicyLocalTie(ImplicitActivationPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public ImplicitActivationPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ImplicitActivationPolicyOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public ImplicitActivationPolicyValue value() {
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

