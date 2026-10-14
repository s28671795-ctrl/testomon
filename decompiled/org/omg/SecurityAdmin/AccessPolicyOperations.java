/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityAdmin;

import org.omg.CORBA.PolicyOperations;
import org.omg.Security.ExtensibleFamily;
import org.omg.Security.Right;
import org.omg.Security.SecAttribute;

public interface AccessPolicyOperations
extends PolicyOperations {
    public Right[] get_effective_rights(SecAttribute[] var1, ExtensibleFamily var2);

    public Right[] get_all_effective_rights(SecAttribute[] var1);
}

