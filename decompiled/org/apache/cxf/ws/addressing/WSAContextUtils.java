/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing;

import org.apache.cxf.message.Message;

public final class WSAContextUtils {
    public static final String REPLYTO_PROPERTY = "org.apache.cxf.ws.addressing.replyto";
    public static final String DECOUPLED_ENDPOINT_BASE_PROPERTY = "org.apache.cxf.ws.addressing.decoupled.endpoint.base";
    private static final String USING_PROPERTY = "org.apache.cxf.ws.addressing.using";

    private WSAContextUtils() {
    }

    public static boolean retrieveUsingAddressing(Message message) {
        Boolean override = (Boolean)message.get(USING_PROPERTY);
        return override == null || override != false;
    }
}

