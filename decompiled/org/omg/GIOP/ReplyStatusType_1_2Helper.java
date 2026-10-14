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
import org.omg.GIOP.ReplyStatusType_1_2;

public abstract class ReplyStatusType_1_2Helper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ReplyStatusType_1_2Helper> clazz = ReplyStatusType_1_2Helper.class;
        synchronized (ReplyStatusType_1_2Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(ReplyStatusType_1_2Helper.id(), "ReplyStatusType_1_2", new String[]{"NO_EXCEPTION", "USER_EXCEPTION", "SYSTEM_EXCEPTION", "LOCATION_FORWARD", "LOCATION_FORWARD_PERM", "NEEDS_ADDRESSING_MODE"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ReplyStatusType_1_2 s2) {
        any.type(ReplyStatusType_1_2Helper.type());
        ReplyStatusType_1_2Helper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ReplyStatusType_1_2 extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ReplyStatusType_1_2 replyStatusType_1_2 = ReplyStatusType_1_2Helper.read(in);
            return replyStatusType_1_2;
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
        return "IDL:omg.org/GIOP/ReplyStatusType_1_2:1.0";
    }

    public static ReplyStatusType_1_2 read(InputStream in) {
        return ReplyStatusType_1_2.from_int(in.read_long());
    }

    public static void write(OutputStream out, ReplyStatusType_1_2 s2) {
        out.write_long(s2.value());
    }
}

