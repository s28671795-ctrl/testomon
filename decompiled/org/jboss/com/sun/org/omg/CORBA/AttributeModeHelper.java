/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.jboss.com.sun.org.omg.CORBA.AttributeMode;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class AttributeModeHelper {
    private static String _id = "IDL:omg.org/CORBA/AttributeMode:1.0";
    private static TypeCode __typeCode = null;

    public static void insert(Any a, AttributeMode that) {
        OutputStream out = a.create_output_stream();
        a.type(AttributeModeHelper.type());
        AttributeModeHelper.write(out, that);
        a.read_value(out.create_input_stream(), AttributeModeHelper.type());
    }

    public static AttributeMode extract(Any a) {
        return AttributeModeHelper.read(a.create_input_stream());
    }

    public static synchronized TypeCode type() {
        if (__typeCode == null) {
            __typeCode = ORB.init().create_enum_tc(AttributeModeHelper.id(), "AttributeMode", new String[]{"ATTR_NORMAL", "ATTR_READONLY"});
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static AttributeMode read(InputStream istream) {
        return AttributeMode.from_int(istream.read_long());
    }

    public static void write(OutputStream ostream, AttributeMode value) {
        ostream.write_long(value.value());
    }
}

