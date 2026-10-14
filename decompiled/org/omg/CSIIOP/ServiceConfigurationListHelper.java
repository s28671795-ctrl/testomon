/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

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
import org.omg.CSIIOP.ServiceConfiguration;
import org.omg.CSIIOP.ServiceConfigurationHelper;
import org.omg.CSIIOP.ServiceConfigurationSyntaxHelper;
import org.omg.CSIIOP.ServiceSpecificNameHelper;

public abstract class ServiceConfigurationListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, ServiceConfiguration[] s2) {
        any.type(ServiceConfigurationListHelper.type());
        ServiceConfigurationListHelper.write(any.create_output_stream(), s2);
    }

    public static ServiceConfiguration[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ServiceConfigurationListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ServiceConfigurationListHelper> clazz = ServiceConfigurationListHelper.class;
        synchronized (ServiceConfigurationListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ServiceConfigurationListHelper.id(), "ServiceConfigurationList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ServiceConfigurationHelper.id(), "ServiceConfiguration", new StructMember[]{new StructMember("syntax", ORB.init().create_alias_tc(ServiceConfigurationSyntaxHelper.id(), "ServiceConfigurationSyntax", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("name", ORB.init().create_alias_tc(ServiceSpecificNameHelper.id(), "ServiceSpecificName", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CSIIOP/ServiceConfigurationList:1.0";
    }

    public static ServiceConfiguration[] read(InputStream _in) {
        int _l_result1 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result1 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result1);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        ServiceConfiguration[] _result = new ServiceConfiguration[_l_result1];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = ServiceConfigurationHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, ServiceConfiguration[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            ServiceConfigurationHelper.write(_out, _s[i]);
        }
    }
}

