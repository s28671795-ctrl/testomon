/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityAdmin;

import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.PolicyOperations;
import org.omg.Security.DelegationMode;

public interface DelegationPolicyOperations
extends PolicyOperations {
    public void set_delegation_mode(InterfaceDef var1, DelegationMode var2);

    public DelegationMode get_delegation_mode(InterfaceDef var1);
}

