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
import org.omg.GIOP.RequestHeader_1_2;
import org.omg.GIOP.RequestReservedHelper;
import org.omg.GIOP.TargetAddressHelper;
import org.omg.IOP.ServiceContextHelper;
import org.omg.IOP.ServiceContextListHelper;
import org.omg.IOP.ServiceIdHelper;

public abstract class RequestHeader_1_2Helper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<RequestHeader_1_2Helper> clazz = RequestHeader_1_2Helper.class;
        synchronized (RequestHeader_1_2Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(RequestHeader_1_2Helper.id(), "RequestHeader_1_2", new StructMember[]{new StructMember("request_id", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("response_flags", ORB.init().get_primitive_tc(TCKind.from_int(10)), null), new StructMember("reserved", ORB.init().create_alias_tc(RequestReservedHelper.id(), "RequestReserved", ORB.init().create_array_tc(3, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null), new StructMember("target", TargetAddressHelper.type(), null), new StructMember("operation", ORB.init().create_string_tc(0), null), new StructMember("service_context", ORB.init().create_alias_tc(ServiceContextListHelper.id(), "ServiceContextList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ServiceContextHelper.id(), "ServiceContext", new StructMember[]{new StructMember("context_id", ORB.init().create_alias_tc(ServiceIdHelper.id(), "ServiceId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("context_data", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))), null)}))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, RequestHeader_1_2 s2) {
        any.type(RequestHeader_1_2Helper.type());
        RequestHeader_1_2Helper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static RequestHeader_1_2 extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            RequestHeader_1_2 requestHeader_1_2 = RequestHeader_1_2Helper.read(in);
            return requestHeader_1_2;
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
        return "IDL:omg.org/GIOP/RequestHeader_1_2:1.0";
    }

    public static RequestHeader_1_2 read(InputStream in) {
        RequestHeader_1_2 result = new RequestHeader_1_2();
        result.request_id = in.read_ulong();
        result.response_flags = in.read_octet();
        result.reserved = RequestReservedHelper.read(in);
        result.target = TargetAddressHelper.read(in);
        result.operation = in.read_string();
        result.service_context = ServiceContextListHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, RequestHeader_1_2 s2) {
        out.write_ulong(s2.request_id);
        out.write_octet(s2.response_flags);
        RequestReservedHelper.write(out, s2.reserved);
        TargetAddressHelper.write(out, s2.target);
        String tmpResult2 = s2.operation;
        out.write_string(tmpResult2);
        ServiceContextListHelper.write(out, s2.service_context);
    }
}

