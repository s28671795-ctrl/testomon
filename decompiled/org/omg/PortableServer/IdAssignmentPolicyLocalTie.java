/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.Policy;
import org.omg.PortableServer.IdAssignmentPolicyOperations;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer._IdAssignmentPolicyLocalBase;

public class IdAssignmentPolicyLocalTie
extends _IdAssignmentPolicyLocalBase {
    private static final long serialVersionUID = 1L;
    private IdAssignmentPolicyOperations _delegate;

    public IdAssignmentPolicyLocalTie(IdAssignmentPolicyOperations delegate) {
        this._delegate = delegate;
    }

    public IdAssignmentPolicyOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(IdAssignmentPolicyOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public IdAssignmentPolicyValue value() {
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

