/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.Hashtable;

public class ValueMemberDefIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("access", "attribute-w;org.omg.CORBA.Visibility");
        irInfo.put("type", "attribute;org.omg.CORBA.TypeCode");
        irInfo.put("type_def", "attribute-w;org.omg.CORBA.IDLType");
    }
}

