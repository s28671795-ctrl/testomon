/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.Policy;
import org.omg.PortableServer.ServantRetentionPolicyOperations;
import org.omg.PortableServer.ServantRetentionPolicyValue;
import org.omg.PortableServer._ServantRetentionPolicyLocalBase;

public class ServantRetentionPolicyLocalTie
extends _ServantRetentionPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private ServantRetentionPolicyOperations _delegate;

    public ServantRetentionPolicyLocalTie(ServantRetentionPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public ServantRetentionPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ServantRetentionPolicyOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public ServantRetentionPolicyValue value() {
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

