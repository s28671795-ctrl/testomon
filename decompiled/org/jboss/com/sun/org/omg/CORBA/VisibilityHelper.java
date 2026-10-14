/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class VisibilityHelper {
    private static String _id = "IDL:omg.org/CORBA/Visibility:1.0";
    private static TypeCode __typeCode = null;

    public static void insert(Any a, short that) {
        OutputStream out = a.create_output_stream();
        a.type(VisibilityHelper.type());
        VisibilityHelper.write(out, that);
        a.read_value(out.create_input_stream(), VisibilityHelper.type());
    }

    public static short extract(Any a) {
        return VisibilityHelper.read(a.create_input_stream());
    }

    public static synchronized TypeCode type() {
        if (__typeCode == null) {
            __typeCode = ORB.init().get_primitive_tc(TCKind.tk_short);
            __typeCode = ORB.init().create_alias_tc(VisibilityHelper.id(), "Visibility", __typeCode);
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static short read(InputStream istream) {
        short value = 0;
        value = istream.read_short();
        return value;
    }

    public static void write(OutputStream ostream, short value) {
        ostream.write_short(value);
    }
}

