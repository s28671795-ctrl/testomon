/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.InvalidPolicies;
import org.omg.CORBA.Policy;
import org.omg.CORBA.SetOverrideType;

public interface PolicyManagerOperations {
    public Policy[] get_policy_overrides(int[] var1);

    public void set_policy_overrides(Policy[] var1, SetOverrideType var2) throws InvalidPolicies;
}

