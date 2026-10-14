/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import java.util.Hashtable;

public class ConnectionIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("read", "(inout:data org.omg.ETF.Buffer,in:offset ,in:min_length ,in:max_length ,in:time_out org.omg.TimeBase.TimeT)");
        irInfo.put("is_data_available", "()");
        irInfo.put("is_connected", "()");
        irInfo.put("use_handle_time_out", "attribute;boolean");
        irInfo.put("supports_callback", "attribute;boolean");
        irInfo.put("wait_next_data", "(in:time_out org.omg.TimeBase.TimeT)");
        irInfo.put("get_server_profile", "()");
        irInfo.put("flush", "()");
        irInfo.put("close", "()");
        irInfo.put("write", "(in:is_first ,in:is_last ,in:data org.omg.ETF.Buffer,in:offset ,in:length ,in:time_out org.omg.TimeBase.TimeT)");
        irInfo.put("connect", "(in:server_profile ,in:time_out org.omg.TimeBase.TimeT)");
    }
}

