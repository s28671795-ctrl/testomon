/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import java.util.Hashtable;

public class IORInfoIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("add_ior_component", "(in:a_component )");
        irInfo.put("get_effective_policy", "(in:type org.omg.CORBA.PolicyType)");
        irInfo.put("add_ior_component_to_profile", "(in:a_component ,in:profile_id org.omg.IOP.ProfileId)");
    }
}

