/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import java.util.Hashtable;

public class ServerRequestInfoIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("set_slot", "(in:id org.omg.PortableInterceptor.SlotId,in:data )");
        irInfo.put("adapter_id", "attribute;org.omg.CORBA.OctetSeq");
        irInfo.put("orb_id", "attribute;org.omg.PortableInterceptor.ORBId");
        irInfo.put("target_most_derived_interface", "attribute;org.omg.CORBA.RepositoryId");
        irInfo.put("sending_exception", "attribute;org.omg.CORBA.Any");
        irInfo.put("get_server_policy", "(in:type org.omg.CORBA.PolicyType)");
        irInfo.put("adapter_name", "attribute;org.omg.PortableInterceptor.AdapterName");
        irInfo.put("server_id", "attribute;org.omg.PortableInterceptor.ServerId");
        irInfo.put("object_id", "attribute;org.omg.PortableInterceptor.ObjectId");
        irInfo.put("target_is_a", "(in:id org.omg.CORBA.RepositoryId)");
        irInfo.put("add_reply_service_context", "(in:service_context ,in:replace )");
    }
}

