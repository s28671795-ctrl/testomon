/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.Hashtable;

public class UnionDefIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("discriminator_type", "attribute;org.omg.CORBA.TypeCode");
        irInfo.put("members", "attribute-w;org.omg.CORBA.UnionMemberSeq");
        irInfo.put("discriminator_type_def", "attribute-w;org.omg.CORBA.IDLType");
    }
}

