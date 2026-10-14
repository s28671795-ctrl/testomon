/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.InvalidPolicies;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyManagerOperations;
import org.omg.CORBA.SetOverrideType;
import org.omg.CORBA._PolicyManagerLocalBase;

public class PolicyManagerLocalTie
extends _PolicyManagerLocalBase {
    private static final long serialVersionUID = 1L;
    private PolicyManagerOperations _delegate;

    public PolicyManagerLocalTie(PolicyManagerOperations delegate) {
        this._delegate = delegate;
    }

    public PolicyManagerOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(PolicyManagerOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public void set_policy_overrides(Policy[] policies, SetOverrideType set_add) throws InvalidPolicies {
        this._delegate.set_policy_overrides(policies, set_add);
    }

    @Override
    public Policy[] get_policy_overrides(int[] ts) {
        return this._delegate.get_policy_overrides(ts);
    }
}

