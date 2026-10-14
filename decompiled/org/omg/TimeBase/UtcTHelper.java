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
import org.omg.TimeBase.TdfTHelper;
import org.omg.TimeBase.TimeTHelper;
import org.omg.TimeBase.UtcT;

public abstract class UtcTHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<UtcTHelper> clazz = UtcTHelper.class;
        synchronized (UtcTHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(UtcTHelper.id(), "UtcT", new StructMember[]{new StructMember("time", ORB.init().create_alias_tc(TimeTHelper.id(), "TimeT", ORB.init().get_primitive_tc(TCKind.from_int(24))), null), new StructMember("inacclo", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("inacchi", ORB.init().get_primitive_tc(TCKind.from_int(4)), null), new StructMember("tdf", ORB.init().create_alias_tc(TdfTHelper.id(), "TdfT", ORB.init().get_primitive_tc(TCKind.from_int(2))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, UtcT s2) {
        any.type(UtcTHelper.type());
        UtcTHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static UtcT extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            UtcT utcT = UtcTHelper.read(in);
            return utcT;
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
        return "IDL:omg.org/TimeBase/UtcT:1.0";
    }

    public static UtcT read(InputStream in) {
        UtcT result = new UtcT();
        result.time = in.read_ulonglong();
        result.inacclo = in.read_ulong();
        result.inacchi = in.read_ushort();
        result.tdf = in.read_short();
        return result;
    }

    public static void write(OutputStream out, UtcT s2) {
        out.write_ulonglong(s2.time);
        out.write_ulong(s2.inacclo);
        out.write_ushort(s2.inacchi);
        out.write_short(s2.tdf);
    }
}

