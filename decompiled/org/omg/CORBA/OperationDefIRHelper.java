/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.Hashtable;

public class OperationDefIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("result", "attribute;org.omg.CORBA.TypeCode");
        irInfo.put("exceptions", "attribute-w;org.omg.CORBA.ExceptionDefSeq");
        irInfo.put("result_def", "attribute-w;org.omg.CORBA.IDLType");
        irInfo.put("mode", "attribute-w;org.omg.CORBA.OperationMode");
        irInfo.put("params", "attribute-w;org.omg.CORBA.ParDescriptionSeq");
        irInfo.put("contexts", "attribute-w;org.omg.CORBA.ContextIdSeq");
    }
}

