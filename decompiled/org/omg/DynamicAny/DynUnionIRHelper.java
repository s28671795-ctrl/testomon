/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import java.util.Hashtable;

public class DynUnionIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("set_discriminator", "(in:d )");
        irInfo.put("member_name", "org.omg.DynamicAny.FieldName()");
        irInfo.put("discriminator_kind", "()");
        irInfo.put("set_to_no_active_member", "()");
        irInfo.put("member_kind", "()");
        irInfo.put("set_to_default_member", "()");
        irInfo.put("member", "()");
        irInfo.put("has_no_active_member", "()");
        irInfo.put("get_discriminator", "()");
    }
}

