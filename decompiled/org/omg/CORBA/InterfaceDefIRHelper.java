/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.Hashtable;

public class InterfaceDefIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("create_attribute", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:type ,in:mode )");
        irInfo.put("describe_interface", "()");
        irInfo.put("base_interfaces", "attribute-w;org.omg.CORBA.InterfaceDefSeq");
        irInfo.put("create_operation", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:result ,in:mode ,in:params org.omg.CORBA.ParDescriptionSeq,in:exceptions org.omg.CORBA.ExceptionDefSeq,in:contexts org.omg.CORBA.ContextIdSeq)");
        irInfo.put("is_a", "(in:interface_id org.omg.CORBA.RepositoryId)");
    }
}

