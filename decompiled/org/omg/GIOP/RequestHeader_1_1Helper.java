/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OctetSeqHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.GIOP.PrincipalHelper;
import org.omg.GIOP.RequestHeader_1_1;
import org.omg.GIOP.RequestReservedHelper;
import org.omg.IOP.ObjectKeyHelper;
import org.omg.IOP.ServiceContextHelper;
import org.omg.IOP.ServiceContextListHelper;
import org.omg.IOP.ServiceIdHelper;

public abstract class RequestHeader_1_1Helper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<RequestHeader_1_1Helper> clazz = RequestHeader_1_1Helper.class;
        synchronized (RequestHeader_1_1Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(RequestHeader_1_1Helper.id(), "RequestHeader_1_1", new StructMember[]{new StructMember("service_context", ORB.init().create_alias_tc(ServiceContextListHelper.id(), "ServiceContextList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ServiceContextHelper.id(), "ServiceContext", new StructMember[]{new StructMember("context_id", ORB.init().create_alias_tc(ServiceIdHelper.id(), "ServiceId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("context_data", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))), null)}))), null), new StructMember("request_id", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("response_expected", ORB.init().get_primitive_tc(TCKind.from_int(8)), null), new StructMember("reserved", ORB.init().create_alias_tc(RequestReservedHelper.id(), "RequestReserved", ORB.init().create_array_tc(3, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null), new StructMember("object_key", ORB.init().create_alias_tc(ObjectKeyHelper.id(), "ObjectKey", ORB.init().create_alias_tc(OctetSeqHelper.id(), "OctetSeq", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))))), null), new StructMember("operation", ORB.init().create_string_tc(0), null), new StructMember("requesting_principal", ORB.init().create_alias_tc(PrincipalHelper.id(), "Principal", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, RequestHeader_1_1 s2) {
        any.type(RequestHeader_1_1Helper.type());
        RequestHeader_1_1Helper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static RequestHeader_1_1 extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            RequestHeader_1_1 requestHeader_1_1 = RequestHeader_1_1Helper.read(in);
            return requestHeader_1_1;
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
        return "IDL:omg.org/GIOP/RequestHeader_1_1:1.0";
    }

    public static RequestHeader_1_1 read(InputStream in) {
        RequestHeader_1_1 result = new RequestHeader_1_1();
        result.service_context = ServiceContextListHelper.read(in);
        result.request_id = in.read_ulong();
        result.response_expected = in.read_boolean();
        result.reserved = RequestReservedHelper.read(in);
        result.object_key = OctetSeqHelper.read(in);
        result.operation = in.read_string();
        result.requesting_principal = PrincipalHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, RequestHeader_1_1 s2) {
        ServiceContextListHelper.write(out, s2.service_context);
        out.write_ulong(s2.request_id);
        out.write_boolean(s2.response_expected);
        RequestReservedHelper.write(out, s2.reserved);
        OctetSeqHelper.write(out, s2.object_key);
        String tmpResult1 = s2.operation;
        out.write_string(tmpResult1);
        PrincipalHelper.write(out, s2.requesting_principal);
    }
}

