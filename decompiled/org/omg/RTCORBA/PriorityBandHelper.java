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
import org.omg.RTCORBA.PriorityBand;
import org.omg.RTCORBA.PriorityHelper;

public abstract class PriorityBandHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PriorityBandHelper> clazz = PriorityBandHelper.class;
        synchronized (PriorityBandHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(PriorityBandHelper.id(), "PriorityBand", new StructMember[]{new StructMember("low", ORB.init().create_alias_tc(PriorityHelper.id(), "Priority", ORB.init().get_primitive_tc(TCKind.from_int(2))), null), new StructMember("high", ORB.init().create_alias_tc(PriorityHelper.id(), "Priority", ORB.init().get_primitive_tc(TCKind.from_int(2))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, PriorityBand s2) {
        any.type(PriorityBandHelper.type());
        PriorityBandHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static PriorityBand extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            PriorityBand priorityBand = PriorityBandHelper.read(in);
            return priorityBand;
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
        return "IDL:omg.org/RTCORBA/PriorityBand:1.0";
    }

    public static PriorityBand read(InputStream in) {
        PriorityBand result = new PriorityBand();
        result.low = in.read_short();
        result.high = in.read_short();
        return result;
    }

    public static void write(OutputStream out, PriorityBand s2) {
        out.write_short(s2.low);
        out.write_short(s2.high);
    }
}

