/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.jboss.com.sun.org.omg.CORBA.ParameterMode;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class ParameterModeHelper {
    private static String _id = "IDL:omg.org/CORBA/ParameterMode:1.0";
    private static TypeCode __typeCode = null;

    public static void insert(Any a, ParameterMode that) {
        OutputStream out = a.create_output_stream();
        a.type(ParameterModeHelper.type());
        ParameterModeHelper.write(out, that);
        a.read_value(out.create_input_stream(), ParameterModeHelper.type());
    }

    public static ParameterMode extract(Any a) {
        return ParameterModeHelper.read(a.create_input_stream());
    }

    public static synchronized TypeCode type() {
        if (__typeCode == null) {
            __typeCode = ORB.init().create_enum_tc(ParameterModeHelper.id(), "ParameterMode", new String[]{"PARAM_IN", "PARAM_OUT", "PARAM_INOUT"});
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static ParameterMode read(InputStream istream) {
        return ParameterMode.from_int(istream.read_long());
    }

    public static void write(OutputStream ostream, ParameterMode value) {
        ostream.write_long(value.value());
    }
}

