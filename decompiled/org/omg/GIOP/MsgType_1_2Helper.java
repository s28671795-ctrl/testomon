/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.GIOP.MsgType_1_1;
import org.omg.GIOP.MsgType_1_1Helper;

public abstract class MsgType_1_2Helper {
    private static volatile TypeCode _type;

    public static void insert(Any any, MsgType_1_1 s2) {
        any.type(MsgType_1_2Helper.type());
        MsgType_1_2Helper.write(any.create_output_stream(), s2);
    }

    public static MsgType_1_1 extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return MsgType_1_2Helper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<MsgType_1_2Helper> clazz = MsgType_1_2Helper.class;
        synchronized (MsgType_1_2Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(MsgType_1_2Helper.id(), "MsgType_1_2", ORB.init().create_enum_tc(MsgType_1_1Helper.id(), "MsgType_1_1", new String[]{"Request", "Reply", "CancelRequest", "LocateRequest", "LocateReply", "CloseConnection", "MessageError", "Fragment"}));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/GIOP/MsgType_1_2:1.0";
    }

    public static MsgType_1_1 read(InputStream _in) {
        MsgType_1_1 _result = MsgType_1_1Helper.read(_in);
        return _result;
    }

    public static void write(OutputStream _out, MsgType_1_1 _s) {
        MsgType_1_1Helper.write(_out, _s);
    }
}

