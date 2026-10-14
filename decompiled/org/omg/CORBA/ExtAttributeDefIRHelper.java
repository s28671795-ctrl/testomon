/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.Hashtable;

public class ExtAttributeDefIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("describe_attribute", "()");
        irInfo.put("set_exceptions", "attribute-w;org.omg.CORBA.ExcDescriptionSeq");
        irInfo.put("get_exceptions", "attribute-w;org.omg.CORBA.ExcDescriptionSeq");
    }
}

