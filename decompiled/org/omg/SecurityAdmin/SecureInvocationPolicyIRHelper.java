/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityAdmin;

import java.util.Hashtable;

public class SecureInvocationPolicyIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("set_association_options", "(in:object_type ,in:requires_supports ,in:direction ,in:options org.omg.Security.AssociationOptions)");
        irInfo.put("get_association_options", "org.omg.Security.AssociationOptions(in:object_type ,in:requires_supports ,in:direction )");
    }
}

