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
import org.omg.GIOP.LocateReplyHeader_1_2;
import org.omg.GIOP.LocateStatusType_1_2Helper;

public abstract class LocateReplyHeader_1_2Helper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<LocateReplyHeader_1_2Helper> clazz = LocateReplyHeader_1_2Helper.class;
        synchronized (LocateReplyHeader_1_2Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(LocateReplyHeader_1_2Helper.id(), "LocateReplyHeader_1_2", new StructMember[]{new StructMember("request_id", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("locate_status", ORB.init().create_enum_tc(LocateStatusType_1_2Helper.id(), "LocateStatusType_1_2", new String[]{"UNKNOWN_OBJECT", "OBJECT_HERE", "OBJECT_FORWARD", "OBJECT_FORWARD_PERM", "LOC_SYSTEM_EXCEPTION", "LOC_NEEDS_ADDRESSING_MODE"}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, LocateReplyHeader_1_2 s2) {
        any.type(LocateReplyHeader_1_2Helper.type());
        LocateReplyHeader_1_2Helper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static LocateReplyHeader_1_2 extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            LocateReplyHeader_1_2 locateReplyHeader_1_2 = LocateReplyHeader_1_2Helper.read(in);
            return locateReplyHeader_1_2;
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
        return "IDL:omg.org/GIOP/LocateReplyHeader_1_2:1.0";
    }

    public static LocateReplyHeader_1_2 read(InputStream in) {
        LocateReplyHeader_1_2 result = new LocateReplyHeader_1_2();
        result.request_id = in.read_ulong();
        result.locate_status = LocateStatusType_1_2Helper.read(in);
        return result;
    }

    public static void write(OutputStream out, LocateReplyHeader_1_2 s2) {
        out.write_ulong(s2.request_id);
        LocateStatusType_1_2Helper.write(out, s2.locate_status);
    }
}

