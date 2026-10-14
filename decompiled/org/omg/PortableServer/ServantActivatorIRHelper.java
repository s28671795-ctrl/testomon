/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.util.Hashtable;

public class ServantActivatorIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("incarnate", "(in:oid org.omg.PortableServer.ObjectId,in:adapter )");
        irInfo.put("etherealize", "(in:oid org.omg.PortableServer.ObjectId,in:adapter ,in:serv ,in:cleanup_in_progress ,in:remaining_activations )");
    }
}

