/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import java.util.Hashtable;

public class CurrentIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("set_slot", "(in:id org.omg.PortableInterceptor.SlotId,in:data )");
        irInfo.put("get_slot", "(in:id org.omg.PortableInterceptor.SlotId)");
    }
}

