/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.util.Hashtable;

public class CurrentIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("get_POA", "()");
        irInfo.put("get_object_id", "org.omg.PortableServer.ObjectId()");
        irInfo.put("get_servant", "()");
        irInfo.put("get_reference", "()");
    }
}

