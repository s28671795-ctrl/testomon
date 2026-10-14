/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.Policy;
import org.omg.RTCORBA.ThreadpoolPolicyOperations;
import org.omg.RTCORBA._ThreadpoolPolicyLocalBase;

public class ThreadpoolPolicyLocalTie
extends _ThreadpoolPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private ThreadpoolPolicyOperations _delegate;

    public ThreadpoolPolicyLocalTie(ThreadpoolPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public ThreadpoolPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ThreadpoolPolicyOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public int threadpool() {
        return this._delegate.threadpool();
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

