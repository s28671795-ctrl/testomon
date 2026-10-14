/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.util.Hashtable;

public class ServantLocatorIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("preinvoke", "(in:oid org.omg.PortableServer.ObjectId,in:adapter ,in:operation org.omg.CORBA.Identifier,out:the_cookie )");
        irInfo.put("postinvoke", "(in:oid org.omg.PortableServer.ObjectId,in:adapter ,in:operation org.omg.CORBA.Identifier,in:the_cookie ,in:the_servant )");
    }
}

