/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTPortableServer;

import java.util.Hashtable;

public class POAIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("activate_object_with_id_and_priority", "(in:oid org.omg.PortableServer.ObjectId,in:p_servant ,in:priority_ org.omg.RTCORBA.Priority)");
        irInfo.put("create_reference_with_id_and_priority", "(in:oid org.omg.PortableServer.ObjectId,in:intf org.omg.CORBA.RepositoryId,in:priority_ org.omg.RTCORBA.Priority)");
        irInfo.put("activate_object_with_priority", "org.omg.PortableServer.ObjectId(in:p_servant ,in:priority_ org.omg.RTCORBA.Priority)");
        irInfo.put("create_reference_with_priority", "(in:intf org.omg.CORBA.RepositoryId,in:priority_ org.omg.RTCORBA.Priority)");
    }
}

