/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.Policy;
import org.omg.PortableServer.IdUniquenessPolicyOperations;
import org.omg.PortableServer.IdUniquenessPolicyValue;
import org.omg.PortableServer._IdUniquenessPolicyLocalBase;

public class IdUniquenessPolicyLocalTie
extends _IdUniquenessPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private IdUniquenessPolicyOperations _delegate;

    public IdUniquenessPolicyLocalTie(IdUniquenessPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public IdUniquenessPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(IdUniquenessPolicyOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public IdUniquenessPolicyValue value() {
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

