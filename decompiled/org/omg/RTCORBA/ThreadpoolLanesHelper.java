/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.RTCORBA.PriorityHelper;
import org.omg.RTCORBA.ThreadpoolLane;
import org.omg.RTCORBA.ThreadpoolLaneHelper;

public abstract class ThreadpoolLanesHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, ThreadpoolLane[] s2) {
        any.type(ThreadpoolLanesHelper.type());
        ThreadpoolLanesHelper.write(any.create_output_stream(), s2);
    }

    public static ThreadpoolLane[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ThreadpoolLanesHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ThreadpoolLanesHelper> clazz = ThreadpoolLanesHelper.class;
        synchronized (ThreadpoolLanesHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ThreadpoolLanesHelper.id(), "ThreadpoolLanes", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ThreadpoolLaneHelper.id(), "ThreadpoolLane", new StructMember[]{new StructMember("lane_priority", ORB.init().create_alias_tc(PriorityHelper.id(), "Priority", ORB.init().get_primitive_tc(TCKind.from_int(2))), null), new StructMember("static_threads", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("dynamic_threads", ORB.init().get_primitive_tc(TCKind.from_int(5)), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/RTCORBA/ThreadpoolLanes:1.0";
    }

    public static ThreadpoolLane[] read(InputStream _in) {
        int _l_result0 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result0 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result0);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        ThreadpoolLane[] _result = new ThreadpoolLane[_l_result0];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = ThreadpoolLaneHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, ThreadpoolLane[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            ThreadpoolLaneHelper.write(_out, _s[i]);
        }
    }
}

