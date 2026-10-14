/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import java.util.Hashtable;

public class DynValueBoxIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("get_boxed_value_as_dyn_any", "()");
        irInfo.put("get_boxed_value", "()");
        irInfo.put("set_boxed_value", "(in:boxed )");
        irInfo.put("set_boxed_value_as_dyn_any", "(in:boxed )");
    }
}

