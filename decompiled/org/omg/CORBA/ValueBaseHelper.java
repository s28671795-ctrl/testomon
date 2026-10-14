/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import java.io.Serializable;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class ValueBaseHelper {
    private static final String id = "IDL:omg.org/CORBA/ValueBase:1.0";
    private static TypeCode typeCode;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void insert(Any any, Serializable value) {
        org.omg.CORBA.portable.OutputStream out = any.create_output_stream();
        try {
            any.type(ValueBaseHelper.type());
            ValueBaseHelper.write(out, value);
            any.read_value(out.create_input_stream(), ValueBaseHelper.type());
        }
        finally {
            try {
                out.close();
            }
            catch (IOException iOException) {}
        }
    }

    public static Serializable extract(Any any) {
        return ValueBaseHelper.read(any.create_input_stream());
    }

    public static synchronized TypeCode type() {
        if (typeCode == null) {
            typeCode = ORB.init().get_primitive_tc(TCKind.tk_value);
        }
        return typeCode;
    }

    public static String id() {
        return id;
    }

    public static Serializable read(org.omg.CORBA.portable.InputStream in) {
        return ((InputStream)in).read_value();
    }

    public static void write(org.omg.CORBA.portable.OutputStream out, Serializable value) {
        ((OutputStream)out).write_value(value);
    }
}

