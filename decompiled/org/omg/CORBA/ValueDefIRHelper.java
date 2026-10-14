/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.Hashtable;

public class ValueDefIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("create_operation", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:result ,in:mode ,in:params org.omg.CORBA.ParDescriptionSeq,in:exceptions org.omg.CORBA.ExceptionDefSeq,in:contexts org.omg.CORBA.ContextIdSeq)");
        irInfo.put("describe_value", "()");
        irInfo.put("abstract_base_values", "attribute-w;org.omg.CORBA.ValueDefSeq");
        irInfo.put("base_value", "attribute-w;org.omg.CORBA.ValueDef");
        irInfo.put("create_value_member", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:type ,in:access org.omg.CORBA.Visibility)");
        irInfo.put("is_a", "(in:id org.omg.CORBA.RepositoryId)");
        irInfo.put("is_custom", "attribute-w;boolean");
        irInfo.put("supported_interfaces", "attribute-w;org.omg.CORBA.InterfaceDefSeq");
        irInfo.put("is_truncatable", "attribute-w;boolean");
        irInfo.put("create_attribute", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:type ,in:mode )");
        irInfo.put("initializers", "attribute-w;org.omg.CORBA.InitializerSeq");
        irInfo.put("is_abstract", "attribute-w;boolean");
    }
}

