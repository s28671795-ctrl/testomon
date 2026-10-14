/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SendingContext;

import java.util.Hashtable;

public class CodeBaseIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("bases", "org.omg.CORBA.RepositoryIdSeq(in:x org.omg.CORBA.RepositoryId)");
        irInfo.put("implementationx", "org.omg.SendingContext.CodeBasePackage.URL(in:x org.omg.CORBA.RepositoryId)");
        irInfo.put("implementation", "org.omg.SendingContext.CodeBasePackage.URL(in:x org.omg.CORBA.RepositoryId)");
        irInfo.put("get_ir", "()");
        irInfo.put("implementations", "org.omg.SendingContext.CodeBasePackage.URLSeq(in:x org.omg.CORBA.RepositoryIdSeq)");
        irInfo.put("meta", "(in:x org.omg.CORBA.RepositoryId)");
        irInfo.put("metas", "org.omg.SendingContext.CodeBasePackage.ValueDescSeq(in:x org.omg.CORBA.RepositoryIdSeq)");
    }
}

