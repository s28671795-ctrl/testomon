/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.util.Hashtable;

public class POAManagerFactoryIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("create_POAManager", "(in:id ,in:policies org.omg.CORBA.PolicyList)");
        irInfo.put("list", "org.omg.PortableServer.POAManagerFactoryPackage.POAManagerSeq()");
        irInfo.put("find", "(in:id )");
    }
}

