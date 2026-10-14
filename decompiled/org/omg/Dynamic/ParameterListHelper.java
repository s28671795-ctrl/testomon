/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Dynamic;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ParameterModeHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Dynamic.Parameter;
import org.omg.Dynamic.ParameterHelper;

public abstract class ParameterListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, Parameter[] s2) {
        any.type(ParameterListHelper.type());
        ParameterListHelper.write(any.create_output_stream(), s2);
    }

    public static Parameter[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ParameterListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ParameterListHelper> clazz = ParameterListHelper.class;
        synchronized (ParameterListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ParameterListHelper.id(), "ParameterList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ParameterHelper.id(), "Parameter", new StructMember[]{new StructMember("argument", ORB.init().get_primitive_tc(TCKind.from_int(11)), null), new StructMember("mode", ORB.init().create_enum_tc(ParameterModeHelper.id(), "ParameterMode", new String[]{"PARAM_IN", "PARAM_OUT", "PARAM_INOUT"}), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/Dynamic/ParameterList:1.0";
    }

    public static Parameter[] read(InputStream _in) {
        int _l_result0 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result0 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result0);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        Parameter[] _result = new Parameter[_l_result0];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = ParameterHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, Parameter[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            ParameterHelper.write(_out, _s[i]);
        }
    }
}

