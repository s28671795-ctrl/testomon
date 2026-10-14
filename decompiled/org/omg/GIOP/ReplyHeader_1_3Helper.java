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
import org.omg.GIOP.ReplyHeader_1_2;
import org.omg.GIOP.ReplyHeader_1_2Helper;
import org.omg.GIOP.ReplyStatusType_1_2Helper;
import org.omg.IOP.ServiceContextHelper;
import org.omg.IOP.ServiceContextListHelper;
import org.omg.IOP.ServiceIdHelper;

public abstract class ReplyHeader_1_3Helper {
    private static volatile TypeCode _type;

    public static void insert(Any any, ReplyHeader_1_2 s2) {
        any.type(ReplyHeader_1_3Helper.type());
        ReplyHeader_1_3Helper.write(any.create_output_stream(), s2);
    }

    public static ReplyHeader_1_2 extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ReplyHeader_1_3Helper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ReplyHeader_1_3Helper> clazz = ReplyHeader_1_3Helper.class;
        synchronized (ReplyHeader_1_3Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ReplyHeader_1_3Helper.id(), "ReplyHeader_1_3", ORB.init().create_struct_tc(ReplyHeader_1_2Helper.id(), "ReplyHeader_1_2", new StructMember[]{new StructMember("request_id", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("reply_status", ORB.init().create_enum_tc(ReplyStatusType_1_2Helper.id(), "ReplyStatusType_1_2", new String[]{"NO_EXCEPTION", "USER_EXCEPTION", "SYSTEM_EXCEPTION", "LOCATION_FORWARD", "LOCATION_FORWARD_PERM", "NEEDS_ADDRESSING_MODE"}), null), new StructMember("service_context", ORB.init().create_alias_tc(ServiceContextListHelper.id(), "ServiceContextList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ServiceContextHelper.id(), "ServiceContext", new StructMember[]{new StructMember("context_id", ORB.init().create_alias_tc(ServiceIdHelper.id(), "ServiceId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("context_data", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))), null)}))), null)}));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/GIOP/ReplyHeader_1_3:1.0";
    }

    public static ReplyHeader_1_2 read(InputStream _in) {
        ReplyHeader_1_2 _result = ReplyHeader_1_2Helper.read(_in);
        return _result;
    }

    public static void write(OutputStream _out, ReplyHeader_1_2 _s) {
        ReplyHeader_1_2Helper.write(_out, _s);
    }
}

