/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityAdmin;

import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.PolicyOperations;
import org.omg.Security.CommunicationDirection;
import org.omg.Security.RequiresSupports;

public interface SecureInvocationPolicyOperations
extends PolicyOperations {
    public void set_association_options(InterfaceDef var1, RequiresSupports var2, CommunicationDirection var3, short var4);

    public short get_association_options(InterfaceDef var1, RequiresSupports var2, CommunicationDirection var3);
}

