/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.jboss.com.sun.org.omg.CORBA.OperationMode;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class OperationModeHelper {
    private static String _id = "IDL:omg.org/CORBA/OperationMode:1.0";
    private static TypeCode __typeCode = null;

    public static void insert(Any a, OperationMode that) {
        OutputStream out = a.create_output_stream();
        a.type(OperationModeHelper.type());
        OperationModeHelper.write(out, that);
        a.read_value(out.create_input_stream(), OperationModeHelper.type());
    }

    public static OperationMode extract(Any a) {
        return OperationModeHelper.read(a.create_input_stream());
    }

    public static synchronized TypeCode type() {
        if (__typeCode == null) {
            __typeCode = ORB.init().create_enum_tc(OperationModeHelper.id(), "OperationMode", new String[]{"OP_NORMAL", "OP_ONEWAY"});
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static OperationMode read(InputStream istream) {
        return OperationMode.from_int(istream.read_long());
    }

    public static void write(OutputStream ostream, OperationMode value) {
        ostream.write_long(value.value());
    }
}

