/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import java.util.Hashtable;

public class TCPProtocolPropertiesIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("send_buffer_size", "attribute-w;int");
        irInfo.put("dont_route", "attribute-w;boolean");
        irInfo.put("no_delay", "attribute-w;boolean");
        irInfo.put("recv_buffer_size", "attribute-w;int");
        irInfo.put("keep_alive", "attribute-w;boolean");
    }
}

