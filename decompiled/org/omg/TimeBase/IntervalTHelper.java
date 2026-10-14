/*
 * Decompiled with CFR 0.152.
 */
package org.omg.TimeBase;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.TimeBase.IntervalT;
import org.omg.TimeBase.TimeTHelper;

public abstract class IntervalTHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<IntervalTHelper> clazz = IntervalTHelper.class;
        synchronized (IntervalTHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(IntervalTHelper.id(), "IntervalT", new StructMember[]{new StructMember("lower_bound", ORB.init().create_alias_tc(TimeTHelper.id(), "TimeT", ORB.init().get_primitive_tc(TCKind.from_int(24))), null), new StructMember("upper_bound", ORB.init().create_alias_tc(TimeTHelper.id(), "TimeT", ORB.init().get_primitive_tc(TCKind.from_int(24))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, IntervalT s2) {
        any.type(IntervalTHelper.type());
        IntervalTHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static IntervalT extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            IntervalT intervalT = IntervalTHelper.read(in);
            return intervalT;
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
        return "IDL:omg.org/TimeBase/IntervalT:1.0";
    }

    public static IntervalT read(InputStream in) {
        IntervalT result = new IntervalT();
        result.lower_bound = in.read_ulonglong();
        result.upper_bound = in.read_ulonglong();
        return result;
    }

    public static void write(OutputStream out, IntervalT s2) {
        out.write_ulonglong(s2.lower_bound);
        out.write_ulonglong(s2.upper_bound);
    }
}

