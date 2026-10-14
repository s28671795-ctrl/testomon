/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import java.util.Hashtable;

public class DynValueIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("set_members", "(in:value org.omg.DynamicAny.NameValuePairSeq)");
        irInfo.put("current_member_kind", "()");
        irInfo.put("get_members", "org.omg.DynamicAny.NameValuePairSeq()");
        irInfo.put("get_members_as_dyn_any", "org.omg.DynamicAny.NameDynAnyPairSeq()");
        irInfo.put("set_members_as_dyn_any", "(in:value org.omg.DynamicAny.NameDynAnyPairSeq)");
        irInfo.put("current_member_name", "org.omg.DynamicAny.FieldName()");
    }
}

