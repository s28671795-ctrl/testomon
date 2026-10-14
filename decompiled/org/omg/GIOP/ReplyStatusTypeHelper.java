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
import org.omg.GIOP.ReplyStatusType_1_2;
import org.omg.GIOP.ReplyStatusType_1_2Helper;

public abstract class ReplyStatusTypeHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, ReplyStatusType_1_2 s2) {
        any.type(ReplyStatusTypeHelper.type());
        ReplyStatusTypeHelper.write(any.create_output_stream(), s2);
    }

    public static ReplyStatusType_1_2 extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ReplyStatusTypeHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ReplyStatusTypeHelper> clazz = ReplyStatusTypeHelper.class;
        synchronized (ReplyStatusTypeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ReplyStatusTypeHelper.id(), "ReplyStatusType", ORB.init().create_enum_tc(ReplyStatusType_1_2Helper.id(), "ReplyStatusType_1_2", new String[]{"NO_EXCEPTION", "USER_EXCEPTION", "SYSTEM_EXCEPTION", "LOCATION_FORWARD", "LOCATION_FORWARD_PERM", "NEEDS_ADDRESSING_MODE"}));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/GIOP/ReplyStatusType:1.0";
    }

    public static ReplyStatusType_1_2 read(InputStream _in) {
        ReplyStatusType_1_2 _result = ReplyStatusType_1_2Helper.read(_in);
        return _result;
    }

    public static void write(OutputStream _out, ReplyStatusType_1_2 _s) {
        ReplyStatusType_1_2Helper.write(_out, _s);
    }
}

