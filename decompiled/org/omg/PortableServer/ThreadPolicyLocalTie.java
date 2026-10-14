/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.Policy;
import org.omg.PortableServer.ThreadPolicyOperations;
import org.omg.PortableServer.ThreadPolicyValue;
import org.omg.PortableServer._ThreadPolicyLocalBase;

public class ThreadPolicyLocalTie
extends _ThreadPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private ThreadPolicyOperations _delegate;

    public ThreadPolicyLocalTie(ThreadPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public ThreadPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ThreadPolicyOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public ThreadPolicyValue value() {
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

