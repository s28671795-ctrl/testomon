/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.InvalidPolicies;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyCurrentOperations;
import org.omg.CORBA.SetOverrideType;
import org.omg.CORBA._PolicyCurrentLocalBase;

public class PolicyCurrentLocalTie
extends _PolicyCurrentLocalBase {
    private static final long serialVersionUID = 1L;
    private PolicyCurrentOperations _delegate;

    public PolicyCurrentLocalTie(PolicyCurrentOperations delegate) {
        this._delegate = delegate;
    }

    public PolicyCurrentOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(PolicyCurrentOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public Policy[] get_policy_overrides(int[] ts) {
        return this._delegate.get_policy_overrides(ts);
    }

    @Override
    public void set_policy_overrides(Policy[] policies, SetOverrideType set_add) throws InvalidPolicies {
        this._delegate.set_policy_overrides(policies, set_add);
    }
}

