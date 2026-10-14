/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.GIOP.LocateReplyHeader_1_0;
import org.omg.GIOP.LocateReplyHeader_1_0Helper;
import org.omg.GIOP.LocateStatusType_1_0Helper;

public abstract class LocateReplyHeader_1_1Helper {
    private static volatile TypeCode _type;

    public static void insert(Any any, LocateReplyHeader_1_0 s2) {
        any.type(LocateReplyHeader_1_1Helper.type());
        LocateReplyHeader_1_1Helper.write(any.create_output_stream(), s2);
    }

    public static LocateReplyHeader_1_0 extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return LocateReplyHeader_1_1Helper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<LocateReplyHeader_1_1Helper> clazz = LocateReplyHeader_1_1Helper.class;
        synchronized (LocateReplyHeader_1_1Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(LocateReplyHeader_1_1Helper.id(), "LocateReplyHeader_1_1", ORB.init().create_struct_tc(LocateReplyHeader_1_0Helper.id(), "LocateReplyHeader_1_0", new StructMember[]{new StructMember("request_id", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("locate_status", ORB.init().create_enum_tc(LocateStatusType_1_0Helper.id(), "LocateStatusType_1_0", new String[]{"UNKNOWN_OBJECT", "OBJECT_HERE", "OBJECT_FORWARD"}), null)}));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/GIOP/LocateReplyHeader_1_1:1.0";
    }

    public static LocateReplyHeader_1_0 read(InputStream _in) {
        LocateReplyHeader_1_0 _result = LocateReplyHeader_1_0Helper.read(_in);
        return _result;
    }

    public static void write(OutputStream _out, LocateReplyHeader_1_0 _s) {
        LocateReplyHeader_1_0Helper.write(_out, _s);
    }
}

