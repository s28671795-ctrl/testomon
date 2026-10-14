/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import java.util.Hashtable;

public class DynEnumIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("get_as_ulong", "()");
        irInfo.put("set_as_string", "(in:value )");
        irInfo.put("set_as_ulong", "(in:value )");
        irInfo.put("get_as_string", "()");
    }
}

