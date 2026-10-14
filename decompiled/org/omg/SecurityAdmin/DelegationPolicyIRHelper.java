/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityAdmin;

import java.util.Hashtable;

public class DelegationPolicyIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("get_delegation_mode", "(in:object_type )");
        irInfo.put("set_delegation_mode", "(in:object_type ,in:mode )");
    }
}

