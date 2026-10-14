/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Any;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyError;
import org.omg.PortableInterceptor.PolicyFactoryOperations;
import org.omg.PortableInterceptor._PolicyFactoryLocalBase;

public class PolicyFactoryLocalTie
extends _PolicyFactoryLocalBase {
    private static final long serialVersionUID = 1L;
    private PolicyFactoryOperations _delegate;

    public PolicyFactoryLocalTie(PolicyFactoryOperations delegate) {
        this._delegate = delegate;
    }

    public PolicyFactoryOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(PolicyFactoryOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public Policy create_policy(int type, Any value) throws PolicyError {
        return this._delegate.create_policy(type, value);
    }
}

