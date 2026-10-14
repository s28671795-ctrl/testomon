/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ParameterMode;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ParameterModeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ParameterModeHelper> clazz = ParameterModeHelper.class;
        synchronized (ParameterModeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(ParameterModeHelper.id(), "ParameterMode", new String[]{"PARAM_IN", "PARAM_OUT", "PARAM_INOUT"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ParameterMode s2) {
        any.type(ParameterModeHelper.type());
        ParameterModeHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ParameterMode extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ParameterMode parameterMode = ParameterModeHelper.read(in);
            return parameterMode;
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
        return "IDL:omg.org/CORBA/ParameterMode:1.0";
    }

    public static ParameterMode read(InputStream in) {
        return ParameterMode.from_int(in.read_long());
    }

    public static void write(OutputStream out, ParameterMode s2) {
        out.write_long(s2.value());
    }
}

