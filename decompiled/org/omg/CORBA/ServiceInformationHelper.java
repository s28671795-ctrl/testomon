/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OctetSeqHelper;
import org.omg.CORBA.ServiceDetailDataHelper;
import org.omg.CORBA.ServiceDetailHelper;
import org.omg.CORBA.ServiceDetailSeqHelper;
import org.omg.CORBA.ServiceDetailTypeHelper;
import org.omg.CORBA.ServiceInformation;
import org.omg.CORBA.ServiceOptionHelper;
import org.omg.CORBA.ServiceOptionSeqHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ServiceInformationHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ServiceInformationHelper> clazz = ServiceInformationHelper.class;
        synchronized (ServiceInformationHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ServiceInformationHelper.id(), "ServiceInformation", new StructMember[]{new StructMember("service_options", ORB.init().create_alias_tc(ServiceOptionSeqHelper.id(), "ServiceOptionSeq", ORB.init().create_sequence_tc(0, ServiceOptionHelper.type())), null), new StructMember("service_details", ORB.init().create_alias_tc(ServiceDetailSeqHelper.id(), "ServiceDetailSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ServiceDetailHelper.id(), "ServiceDetail", new StructMember[]{new StructMember("service_detail_type", ORB.init().create_alias_tc(ServiceDetailTypeHelper.id(), "ServiceDetailType", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("service_detail", ORB.init().create_alias_tc(ServiceDetailDataHelper.id(), "ServiceDetailData", ORB.init().create_alias_tc(OctetSeqHelper.id(), "OctetSeq", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))))), null)}))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ServiceInformation s2) {
        any.type(ServiceInformationHelper.type());
        ServiceInformationHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ServiceInformation extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ServiceInformation serviceInformation = ServiceInformationHelper.read(in);
            return serviceInformation;
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
        return "IDL:omg.org/CORBA/ServiceInformation:1.0";
    }

    public static ServiceInformation read(InputStream in) {
        ServiceInformation result = new ServiceInformation();
        result.service_options = ServiceOptionSeqHelper.read(in);
        result.service_details = ServiceDetailSeqHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, ServiceInformation s2) {
        ServiceOptionSeqHelper.write(out, s2.service_options);
        ServiceDetailSeqHelper.write(out, s2.service_details);
    }
}

