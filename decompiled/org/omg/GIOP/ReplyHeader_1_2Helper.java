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
import org.omg.GIOP.ReplyHeader_1_2;
import org.omg.GIOP.ReplyStatusType_1_2Helper;
import org.omg.IOP.ServiceContextHelper;
import org.omg.IOP.ServiceContextListHelper;
import org.omg.IOP.ServiceIdHelper;

public abstract class ReplyHeader_1_2Helper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ReplyHeader_1_2Helper> clazz = ReplyHeader_1_2Helper.class;
        synchronized (ReplyHeader_1_2Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ReplyHeader_1_2Helper.id(), "ReplyHeader_1_2", new StructMember[]{new StructMember("request_id", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("reply_status", ORB.init().create_enum_tc(ReplyStatusType_1_2Helper.id(), "ReplyStatusType_1_2", new String[]{"NO_EXCEPTION", "USER_EXCEPTION", "SYSTEM_EXCEPTION", "LOCATION_FORWARD", "LOCATION_FORWARD_PERM", "NEEDS_ADDRESSING_MODE"}), null), new StructMember("service_context", ORB.init().create_alias_tc(ServiceContextListHelper.id(), "ServiceContextList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ServiceContextHelper.id(), "ServiceContext", new StructMember[]{new StructMember("context_id", ORB.init().create_alias_tc(ServiceIdHelper.id(), "ServiceId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("context_data", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))), null)}))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ReplyHeader_1_2 s2) {
        any.type(ReplyHeader_1_2Helper.type());
        ReplyHeader_1_2Helper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ReplyHeader_1_2 extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ReplyHeader_1_2 replyHeader_1_2 = ReplyHeader_1_2Helper.read(in);
            return replyHeader_1_2;
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
        return "IDL:omg.org/GIOP/ReplyHeader_1_2:1.0";
    }

    public static ReplyHeader_1_2 read(InputStream in) {
        ReplyHeader_1_2 result = new ReplyHeader_1_2();
        result.request_id = in.read_ulong();
        result.reply_status = ReplyStatusType_1_2Helper.read(in);
        result.service_context = ServiceContextListHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, ReplyHeader_1_2 s2) {
        out.write_ulong(s2.request_id);
        ReplyStatusType_1_2Helper.write(out, s2.reply_status);
        ServiceContextListHelper.write(out, s2.service_context);
    }
}

