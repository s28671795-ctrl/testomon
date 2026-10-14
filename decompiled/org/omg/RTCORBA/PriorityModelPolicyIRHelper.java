/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import java.util.Hashtable;

public class PriorityModelPolicyIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("server_priority", "attribute;org.omg.RTCORBA.Priority");
        irInfo.put("priority_model", "attribute;org.omg.RTCORBA.PriorityModel");
    }
}

