/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.IOP.ServiceContext;
import org.omg.IOP.ServiceIdHelper;

public abstract class ServiceContextHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ServiceContextHelper> clazz = ServiceContextHelper.class;
        synchronized (ServiceContextHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ServiceContextHelper.id(), "ServiceContext", new StructMember[]{new StructMember("context_id", ORB.init().create_alias_tc(ServiceIdHelper.id(), "ServiceId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("context_data", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ServiceContext s2) {
        any.type(ServiceContextHelper.type());
        ServiceContextHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ServiceContext extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ServiceContext serviceContext = ServiceContextHelper.read(in);
            return serviceContext;
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
        return "IDL:omg.org/IOP/ServiceContext:1.0";
    }

    public static ServiceContext read(InputStream in) {
        ServiceContext result = new ServiceContext();
        result.context_id = in.read_ulong();
        int _lresult_context_data5 = in.read_long();
        try {
            int x = in.available();
            if (x > 0 && _lresult_context_data5 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _lresult_context_data5);
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        result.context_data = new byte[_lresult_context_data5];
        in.read_octet_array(result.context_data, 0, _lresult_context_data5);
        return result;
    }

    public static void write(OutputStream out, ServiceContext s2) {
        out.write_ulong(s2.context_id);
        out.write_long(s2.context_data.length);
        out.write_octet_array(s2.context_data, 0, s2.context_data.length);
    }
}

