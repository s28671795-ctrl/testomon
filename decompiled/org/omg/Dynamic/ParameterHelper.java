/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Dynamic;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ParameterModeHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Dynamic.Parameter;

public abstract class ParameterHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ParameterHelper> clazz = ParameterHelper.class;
        synchronized (ParameterHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ParameterHelper.id(), "Parameter", new StructMember[]{new StructMember("argument", ORB.init().get_primitive_tc(TCKind.from_int(11)), null), new StructMember("mode", ORB.init().create_enum_tc(ParameterModeHelper.id(), "ParameterMode", new String[]{"PARAM_IN", "PARAM_OUT", "PARAM_INOUT"}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, Parameter s2) {
        any.type(ParameterHelper.type());
        ParameterHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Parameter extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            Parameter parameter = ParameterHelper.read(in);
            return parameter;
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
        return "IDL:omg.org/Dynamic/Parameter:1.0";
    }

    public static Parameter read(InputStream in) {
        Parameter result = new Parameter();
        result.argument = in.read_any();
        result.mode = ParameterModeHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, Parameter s2) {
        out.write_any(s2.argument);
        ParameterModeHelper.write(out, s2.mode);
    }
}

