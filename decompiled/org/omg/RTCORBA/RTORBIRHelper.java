/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import java.util.Hashtable;

public class RTORBIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("create_threadpool_policy", "(in:threadpool org.omg.RTCORBA.ThreadpoolId)");
        irInfo.put("create_threadpool_with_lanes", "org.omg.RTCORBA.ThreadpoolId(in:stacksize ,in:lanes org.omg.RTCORBA.ThreadpoolLanes,in:allow_borrowing ,in:allow_request_buffering ,in:max_buffered_requests ,in:max_request_buffer_size )");
        irInfo.put("create_mutex", "()");
        irInfo.put("create_threadpool", "org.omg.RTCORBA.ThreadpoolId(in:stacksize ,in:static_threads ,in:dynamic_threads ,in:default_priority org.omg.RTCORBA.Priority,in:allow_request_buffering ,in:max_buffered_requests ,in:max_request_buffer_size )");
        irInfo.put("destroy_mutex", "(in:the_mutex )");
        irInfo.put("destroy_threadpool", "(in:threadpool org.omg.RTCORBA.ThreadpoolId)");
        irInfo.put("create_priority_banded_connection_policy", "(in:priority_bands org.omg.RTCORBA.PriorityBands)");
        irInfo.put("create_server_protocol_policy", "(in:protocols org.omg.RTCORBA.ProtocolList)");
        irInfo.put("create_tcp_protocol_properties", "(in:send_buffer_size ,in:recv_buffer_size ,in:keep_alive ,in:dont_route ,in:no_delay )");
        irInfo.put("create_private_connection_policy", "()");
        irInfo.put("create_priority_model_policy", "(in:priority_model ,in:server_priority org.omg.RTCORBA.Priority)");
        irInfo.put("create_client_protocol_policy", "(in:protocols org.omg.RTCORBA.ProtocolList)");
    }
}

