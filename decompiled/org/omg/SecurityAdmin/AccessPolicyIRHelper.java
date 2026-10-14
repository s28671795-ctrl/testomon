/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityAdmin;

import java.util.Hashtable;

public class AccessPolicyIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("get_effective_rights", "org.omg.Security.RightsList(in:attrib_list org.omg.Security.AttributeList,in:rights_family )");
        irInfo.put("get_all_effective_rights", "org.omg.Security.RightsList(in:attrib_list org.omg.Security.AttributeList)");
    }
}

