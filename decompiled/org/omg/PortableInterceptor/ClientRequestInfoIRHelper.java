/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import java.util.Hashtable;

public class ClientRequestInfoIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("effective_target", "attribute;org.omg.CORBA.Object");
        irInfo.put("received_exception_id", "attribute;org.omg.CORBA.RepositoryId");
        irInfo.put("get_request_policy", "(in:type org.omg.CORBA.PolicyType)");
        irInfo.put("get_effective_components", "org.omg.IOP.TaggedComponentSeq(in:id org.omg.IOP.ComponentId)");
        irInfo.put("target", "attribute;org.omg.CORBA.Object");
        irInfo.put("add_request_service_context", "(in:service_context ,in:replace )");
        irInfo.put("get_effective_component", "(in:id org.omg.IOP.ComponentId)");
        irInfo.put("effective_profile", "attribute;org.omg.IOP.TaggedProfile");
        irInfo.put("received_exception", "attribute;org.omg.CORBA.Any");
    }
}

