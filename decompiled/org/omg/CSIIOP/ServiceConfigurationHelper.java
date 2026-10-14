/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CSIIOP.ServiceConfiguration;
import org.omg.CSIIOP.ServiceConfigurationSyntaxHelper;
import org.omg.CSIIOP.ServiceSpecificNameHelper;

public abstract class ServiceConfigurationHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ServiceConfigurationHelper> clazz = ServiceConfigurationHelper.class;
        synchronized (ServiceConfigurationHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ServiceConfigurationHelper.id(), "ServiceConfiguration", new StructMember[]{new StructMember("syntax", ORB.init().create_alias_tc(ServiceConfigurationSyntaxHelper.id(), "ServiceConfigurationSyntax", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("name", ORB.init().create_alias_tc(ServiceSpecificNameHelper.id(), "ServiceSpecificName", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ServiceConfiguration s2) {
        any.type(ServiceConfigurationHelper.type());
        ServiceConfigurationHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ServiceConfiguration extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ServiceConfiguration serviceConfiguration = ServiceConfigurationHelper.read(in);
            return serviceConfiguration;
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
        return "IDL:omg.org/CSIIOP/ServiceConfiguration:1.0";
    }

    public static ServiceConfiguration read(InputStream in) {
        ServiceConfiguration result = new ServiceConfiguration();
        result.syntax = in.read_ulong();
        result.name = ServiceSpecificNameHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, ServiceConfiguration s2) {
        out.write_ulong(s2.syntax);
        ServiceSpecificNameHelper.write(out, s2.name);
    }
}

