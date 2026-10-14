/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityAdmin;

import org.omg.Security.DelegationState;
import org.omg.Security.ExtensibleFamily;
import org.omg.Security.Right;
import org.omg.Security.SecAttribute;
import org.omg.SecurityAdmin.AccessPolicyOperations;

public interface DomainAccessPolicyOperations
extends AccessPolicyOperations {
    public void grant_rights(SecAttribute var1, DelegationState var2, Right[] var3);

    public void revoke_rights(SecAttribute var1, DelegationState var2, Right[] var3);

    public void replace_rights(SecAttribute var1, DelegationState var2, Right[] var3);

    public Right[] get_rights(SecAttribute var1, DelegationState var2, ExtensibleFamily var3);

    public Right[] get_all_rights(SecAttribute var1, DelegationState var2);
}

