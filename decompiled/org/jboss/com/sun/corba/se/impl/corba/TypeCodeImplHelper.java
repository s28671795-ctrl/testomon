/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.corba;

import org.jboss.com.sun.corba.se.impl.corba.TypeCodeImpl;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class TypeCodeImplHelper {
    private static String _id = "IDL:omg.org/CORBA/TypeCode:1.0";
    private static TypeCode __typeCode = null;

    public static void insert(Any a, TypeCode that) {
        OutputStream out = a.create_output_stream();
        a.type(TypeCodeImplHelper.type());
        TypeCodeImplHelper.write(out, that);
        a.read_value(out.create_input_stream(), TypeCodeImplHelper.type());
    }

    public static TypeCode extract(Any a) {
        return TypeCodeImplHelper.read(a.create_input_stream());
    }

    public static synchronized TypeCode type() {
        if (__typeCode == null) {
            __typeCode = ORB.init().get_primitive_tc(TCKind.tk_TypeCode);
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static TypeCode read(InputStream istream) {
        return istream.read_TypeCode();
    }

    public static void write(OutputStream ostream, TypeCode value) {
        ostream.write_TypeCode(value);
    }

    public static void write(OutputStream ostream, TypeCodeImpl value) {
        ostream.write_TypeCode(value);
    }
}

