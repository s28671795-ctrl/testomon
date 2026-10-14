/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.RTCORBA.PriorityHelper;
import org.omg.RTCORBA.ThreadpoolLane;

public abstract class ThreadpoolLaneHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ThreadpoolLaneHelper> clazz = ThreadpoolLaneHelper.class;
        synchronized (ThreadpoolLaneHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ThreadpoolLaneHelper.id(), "ThreadpoolLane", new StructMember[]{new StructMember("lane_priority", ORB.init().create_alias_tc(PriorityHelper.id(), "Priority", ORB.init().get_primitive_tc(TCKind.from_int(2))), null), new StructMember("static_threads", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("dynamic_threads", ORB.init().get_primitive_tc(TCKind.from_int(5)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ThreadpoolLane s2) {
        any.type(ThreadpoolLaneHelper.type());
        ThreadpoolLaneHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ThreadpoolLane extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ThreadpoolLane threadpoolLane = ThreadpoolLaneHelper.read(in);
            return threadpoolLane;
        }
        finally {
            try {
                in.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Unexpected exception " + e.toString());
            }
        }
    }

    public static String id() {
        return "IDL:omg.org/RTCORBA/ThreadpoolLane:1.0";
    }

    public static ThreadpoolLane read(InputStream in) {
        ThreadpoolLane result = new ThreadpoolLane();
        result.lane_priority = in.read_short();
        result.static_threads = in.read_ulong();
        result.dynamic_threads = in.read_ulong();
        return result;
    }

    public static void write(OutputStream out, ThreadpoolLane s2) {
        out.write_short(s2.lane_priority);
        out.write_ulong(s2.static_threads);
        out.write_ulong(s2.dynamic_threads);
    }
}

