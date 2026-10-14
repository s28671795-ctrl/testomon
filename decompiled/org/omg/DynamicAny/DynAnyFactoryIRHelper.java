/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import java.util.Hashtable;

public class DynAnyFactoryIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("create_dyn_any", "(in:value )");
        irInfo.put("create_dyn_any_without_truncation", "(in:value )");
        irInfo.put("create_multiple_anys", "org.omg.DynamicAny.AnySeq(in:values org.omg.DynamicAny.DynAnySeq)");
        irInfo.put("create_multiple_dyn_anys", "org.omg.DynamicAny.DynAnySeq(in:values org.omg.DynamicAny.AnySeq,in:allow_truncate )");
        irInfo.put("create_dyn_any_from_type_code", "(in:type )");
    }
}

