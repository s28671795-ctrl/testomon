/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.GIOP.LocateRequestHeader_1_2;
import org.omg.GIOP.TargetAddressHelper;

public abstract class LocateRequestHeader_1_2Helper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<LocateRequestHeader_1_2Helper> clazz = LocateRequestHeader_1_2Helper.class;
        synchronized (LocateRequestHeader_1_2Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(LocateRequestHeader_1_2Helper.id(), "LocateRequestHeader_1_2", new StructMember[]{new StructMember("request_id", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("target", TargetAddressHelper.type(), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, LocateRequestHeader_1_2 s2) {
        any.type(LocateRequestHeader_1_2Helper.type());
        LocateRequestHeader_1_2Helper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static LocateRequestHeader_1_2 extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            LocateRequestHeader_1_2 locateRequestHeader_1_2 = LocateRequestHeader_1_2Helper.read(in);
            return locateRequestHeader_1_2;
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
        return "IDL:omg.org/GIOP/LocateRequestHeader_1_2:1.0";
    }

    public static LocateRequestHeader_1_2 read(InputStream in) {
        LocateRequestHeader_1_2 result = new LocateRequestHeader_1_2();
        result.request_id = in.read_ulong();
        result.target = TargetAddressHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, LocateRequestHeader_1_2 s2) {
        out.write_ulong(s2.request_id);
        TargetAddressHelper.write(out, s2.target);
    }
}

