/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import java.util.Hashtable;

public class ConnectionZeroCopyIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("write_zc", "(inout:data ,in:time_out org.omg.TimeBase.TimeT)");
        irInfo.put("read_zc", "(inout:data ,in:min_length ,in:time_out org.omg.TimeBase.TimeT)");
        irInfo.put("create_buffer_list", "()");
    }
}

