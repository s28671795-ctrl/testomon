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
import org.omg.GIOP.MsgType_1_1;

public abstract class MsgType_1_1Helper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<MsgType_1_1Helper> clazz = MsgType_1_1Helper.class;
        synchronized (MsgType_1_1Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(MsgType_1_1Helper.id(), "MsgType_1_1", new String[]{"Request", "Reply", "CancelRequest", "LocateRequest", "LocateReply", "CloseConnection", "MessageError", "Fragment"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, MsgType_1_1 s2) {
        any.type(MsgType_1_1Helper.type());
        MsgType_1_1Helper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static MsgType_1_1 extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            MsgType_1_1 msgType_1_1 = MsgType_1_1Helper.read(in);
            return msgType_1_1;
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
        return "IDL:omg.org/GIOP/MsgType_1_1:1.0";
    }

    public static MsgType_1_1 read(InputStream in) {
        return MsgType_1_1.from_int(in.read_long());
    }

    public static void write(OutputStream out, MsgType_1_1 s2) {
        out.write_long(s2.value());
    }
}

