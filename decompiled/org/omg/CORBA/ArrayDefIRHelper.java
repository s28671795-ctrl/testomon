/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.Hashtable;

public class ArrayDefIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("element_type_def", "attribute-w;org.omg.CORBA.IDLType");
        irInfo.put("length", "attribute-w;int");
        irInfo.put("element_type", "attribute;org.omg.CORBA.TypeCode");
    }
}

