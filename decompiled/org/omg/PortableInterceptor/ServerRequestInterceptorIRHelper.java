/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import java.util.Hashtable;

public class ServerRequestInterceptorIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("receive_request_service_contexts", "(in:ri )");
        irInfo.put("send_reply", "(in:ri )");
        irInfo.put("receive_request", "(in:ri )");
        irInfo.put("send_other", "(in:ri )");
        irInfo.put("send_exception", "(in:ri )");
    }
}

