/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.Hashtable;

public class TypeCodeIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("length", "()");
        irInfo.put("member_name", "org.omg.CORBA.Identifier(in:index )");
        irInfo.put("member_visibility", "org.omg.CORBA.Visibility(in:index )");
        irInfo.put("type_modifier", "org.omg.CORBA.ValueModifier()");
        irInfo.put("kind", "()");
        irInfo.put("member_type", "(in:index )");
        irInfo.put("member_count", "()");
        irInfo.put("concrete_base_type", "()");
        irInfo.put("member_label", "(in:index )");
        irInfo.put("content_type", "()");
        irInfo.put("default_index", "()");
        irInfo.put("get_compact_typecode", "()");
        irInfo.put("equal", "(in:tc )");
        irInfo.put("equivalent", "(in:tc )");
        irInfo.put("id", "org.omg.CORBA.RepositoryId()");
        irInfo.put("fixed_digits", "()");
        irInfo.put("fixed_scale", "()");
        irInfo.put("discriminator_type", "()");
        irInfo.put("name", "org.omg.CORBA.Identifier()");
    }
}

