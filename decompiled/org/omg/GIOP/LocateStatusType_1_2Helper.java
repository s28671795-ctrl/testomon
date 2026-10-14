/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.GIOP.LocateStatusType_1_2;

public abstract class LocateStatusType_1_2Helper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<LocateStatusType_1_2Helper> clazz = LocateStatusType_1_2Helper.class;
        synchronized (LocateStatusType_1_2Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(LocateStatusType_1_2Helper.id(), "LocateStatusType_1_2", new String[]{"UNKNOWN_OBJECT", "OBJECT_HERE", "OBJECT_FORWARD", "OBJECT_FORWARD_PERM", "LOC_SYSTEM_EXCEPTION", "LOC_NEEDS_ADDRESSING_MODE"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, LocateStatusType_1_2 s2) {
        any.type(LocateStatusType_1_2Helper.type());
        LocateStatusType_1_2Helper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static LocateStatusType_1_2 extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            LocateStatusType_1_2 locateStatusType_1_2 = LocateStatusType_1_2Helper.read(in);
            return locateStatusType_1_2;
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
        return "IDL:omg.org/GIOP/LocateStatusType_1_2:1.0";
    }

    public static LocateStatusType_1_2 read(InputStream in) {
        return LocateStatusType_1_2.from_int(in.read_long());
    }

    public static void write(OutputStream out, LocateStatusType_1_2 s2) {
        out.write_long(s2.value());
    }
}

