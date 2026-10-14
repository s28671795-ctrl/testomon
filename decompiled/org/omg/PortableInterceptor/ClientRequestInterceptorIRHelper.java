/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import java.util.Hashtable;

public class ClientRequestInterceptorIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("receive_exception", "(in:ri )");
        irInfo.put("receive_reply", "(in:ri )");
        irInfo.put("send_poll", "(in:ri )");
        irInfo.put("receive_other", "(in:ri )");
        irInfo.put("send_request", "(in:ri )");
    }
}

