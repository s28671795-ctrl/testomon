/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.Hashtable;

public class ContainedIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("move", "(in:new_container ,in:new_name org.omg.CORBA.Identifier,in:new_version org.omg.CORBA.VersionSpec)");
        irInfo.put("defined_in", "attribute;org.omg.CORBA.Container");
        irInfo.put("version", "attribute-w;org.omg.CORBA.VersionSpec");
        irInfo.put("containing_repository", "attribute;org.omg.CORBA.Repository");
        irInfo.put("absolute_name", "attribute;org.omg.CORBA.ScopedName");
        irInfo.put("name", "attribute-w;org.omg.CORBA.Identifier");
        irInfo.put("describe", "()");
        irInfo.put("id", "attribute-w;org.omg.CORBA.RepositoryId");
    }
}

