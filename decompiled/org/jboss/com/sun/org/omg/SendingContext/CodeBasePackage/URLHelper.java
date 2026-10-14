/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.SendingContext.CodeBasePackage;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class URLHelper {
    private static String _id = "IDL:omg.org/SendingContext/CodeBase/URL:1.0";
    private static TypeCode __typeCode = null;

    public static void insert(Any a, String that) {
        OutputStream out = a.create_output_stream();
        a.type(URLHelper.type());
        URLHelper.write(out, that);
        a.read_value(out.create_input_stream(), URLHelper.type());
    }

    public static String extract(Any a) {
        return URLHelper.read(a.create_input_stream());
    }

    public static synchronized TypeCode type() {
        if (__typeCode == null) {
            __typeCode = ORB.init().create_string_tc(0);
            __typeCode = ORB.init().create_alias_tc(URLHelper.id(), "URL", __typeCode);
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static String read(InputStream istream) {
        String value = null;
        value = istream.read_string();
        return value;
    }

    public static void write(OutputStream ostream, String value) {
        ostream.write_string(value);
    }
}

