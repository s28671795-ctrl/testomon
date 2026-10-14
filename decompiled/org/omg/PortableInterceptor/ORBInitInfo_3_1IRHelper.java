/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import java.util.Hashtable;

public class ORBInitInfo_3_1IRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("add_ior_interceptor_with_policy", "(in:interceptor ,in:policies org.omg.CORBA.PolicyList)");
        irInfo.put("add_server_request_interceptor_with_policy", "(in:interceptor ,in:policies org.omg.CORBA.PolicyList)");
        irInfo.put("add_client_request_interceptor_with_policy", "(in:interceptor ,in:policies org.omg.CORBA.PolicyList)");
    }
}

