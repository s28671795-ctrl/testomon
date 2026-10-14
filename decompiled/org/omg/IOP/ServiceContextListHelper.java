/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.IOP.ServiceContext;
import org.omg.IOP.ServiceContextHelper;
import org.omg.IOP.ServiceIdHelper;

public abstract class ServiceContextListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, ServiceContext[] s2) {
        any.type(ServiceContextListHelper.type());
        ServiceContextListHelper.write(any.create_output_stream(), s2);
    }

    public static ServiceContext[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ServiceContextListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ServiceContextListHelper> clazz = ServiceContextListHelper.class;
        synchronized (ServiceContextListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ServiceContextListHelper.id(), "ServiceContextList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ServiceContextHelper.id(), "ServiceContext", new StructMember[]{new StructMember("context_id", ORB.init().create_alias_tc(ServiceIdHelper.id(), "ServiceId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("context_data", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/IOP/ServiceContextList:1.0";
    }

    public static ServiceContext[] read(InputStream _in) {
        int _l_result6 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result6 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result6);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        ServiceContext[] _result = new ServiceContext[_l_result6];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = ServiceContextHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, ServiceContext[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            ServiceContextHelper.write(_out, _s[i]);
        }
    }
}

