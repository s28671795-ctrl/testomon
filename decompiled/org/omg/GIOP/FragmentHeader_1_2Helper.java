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
import org.omg.GIOP.FragmentHeader_1_2;

public abstract class FragmentHeader_1_2Helper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<FragmentHeader_1_2Helper> clazz = FragmentHeader_1_2Helper.class;
        synchronized (FragmentHeader_1_2Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(FragmentHeader_1_2Helper.id(), "FragmentHeader_1_2", new StructMember[]{new StructMember("request_id", ORB.init().get_primitive_tc(TCKind.from_int(5)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, FragmentHeader_1_2 s2) {
        any.type(FragmentHeader_1_2Helper.type());
        FragmentHeader_1_2Helper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static FragmentHeader_1_2 extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            FragmentHeader_1_2 fragmentHeader_1_2 = FragmentHeader_1_2Helper.read(in);
            return fragmentHeader_1_2;
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
        return "IDL:omg.org/GIOP/FragmentHeader_1_2:1.0";
    }

    public static FragmentHeader_1_2 read(InputStream in) {
        FragmentHeader_1_2 result = new FragmentHeader_1_2();
        result.request_id = in.read_ulong();
        return result;
    }

    public static void write(OutputStream out, FragmentHeader_1_2 s2) {
        out.write_ulong(s2.request_id);
    }
}

