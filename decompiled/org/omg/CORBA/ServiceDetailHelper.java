/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OctetSeqHelper;
import org.omg.CORBA.ServiceDetail;
import org.omg.CORBA.ServiceDetailDataHelper;
import org.omg.CORBA.ServiceDetailTypeHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ServiceDetailHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ServiceDetailHelper> clazz = ServiceDetailHelper.class;
        synchronized (ServiceDetailHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ServiceDetailHelper.id(), "ServiceDetail", new StructMember[]{new StructMember("service_detail_type", ORB.init().create_alias_tc(ServiceDetailTypeHelper.id(), "ServiceDetailType", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("service_detail", ORB.init().create_alias_tc(ServiceDetailDataHelper.id(), "ServiceDetailData", ORB.init().create_alias_tc(OctetSeqHelper.id(), "OctetSeq", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ServiceDetail s2) {
        any.type(ServiceDetailHelper.type());
        ServiceDetailHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ServiceDetail extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ServiceDetail serviceDetail = ServiceDetailHelper.read(in);
            return serviceDetail;
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
        return "IDL:omg.org/CORBA/ServiceDetail:1.0";
    }

    public static ServiceDetail read(InputStream in) {
        ServiceDetail result = new ServiceDetail();
        result.service_detail_type = in.read_ulong();
        result.service_detail = OctetSeqHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, ServiceDetail s2) {
        out.write_ulong(s2.service_detail_type);
        OctetSeqHelper.write(out, s2.service_detail);
    }
}

