/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import java.util.Hashtable;

public class DynSequenceIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("set_elements", "(in:value org.omg.DynamicAny.AnySeq)");
        irInfo.put("set_elements_as_dyn_any", "(in:value org.omg.DynamicAny.DynAnySeq)");
        irInfo.put("set_length", "(in:len )");
        irInfo.put("get_elements_as_dyn_any", "org.omg.DynamicAny.DynAnySeq()");
        irInfo.put("get_length", "()");
        irInfo.put("get_elements", "org.omg.DynamicAny.AnySeq()");
    }
}

