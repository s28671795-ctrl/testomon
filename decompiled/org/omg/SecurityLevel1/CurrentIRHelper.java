/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel1;

import java.util.Hashtable;

public class CurrentIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("get_attributes", "org.omg.Security.AttributeList(in:attributes org.omg.Security.AttributeTypeList)");
    }
}

