/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.Hashtable;

public class AttributeDefIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("type", "attribute;org.omg.CORBA.TypeCode");
        irInfo.put("mode", "attribute-w;org.omg.CORBA.AttributeMode");
        irInfo.put("type_def", "attribute-w;org.omg.CORBA.IDLType");
    }
}

