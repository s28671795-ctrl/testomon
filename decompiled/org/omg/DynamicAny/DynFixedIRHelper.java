/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import java.util.Hashtable;

public class DynFixedIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("get_value", "()");
        irInfo.put("set_value", "(in:val )");
    }
}

