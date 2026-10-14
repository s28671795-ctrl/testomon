/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.Hashtable;

public class ExtValueDefIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("create_ext_attribute", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:type ,in:mode ,in:get_exceptions org.omg.CORBA.ExceptionDefSeq,in:set_exceptions org.omg.CORBA.ExceptionDefSeq)");
        irInfo.put("describe_ext_value", "()");
        irInfo.put("ext_initializers", "attribute-w;org.omg.CORBA.ExtInitializerSeq");
    }
}

