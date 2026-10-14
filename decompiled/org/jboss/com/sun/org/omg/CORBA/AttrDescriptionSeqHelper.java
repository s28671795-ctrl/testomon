/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.jboss.com.sun.org.omg.CORBA.AttributeDescription;
import org.jboss.com.sun.org.omg.CORBA.AttributeDescriptionHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class AttrDescriptionSeqHelper {
    private static String _id = "IDL:omg.org/CORBA/AttrDescriptionSeq:1.0";
    private static TypeCode __typeCode = null;

    public static void insert(Any a, AttributeDescription[] that) {
        OutputStream out = a.create_output_stream();
        a.type(AttrDescriptionSeqHelper.type());
        AttrDescriptionSeqHelper.write(out, that);
        a.read_value(out.create_input_stream(), AttrDescriptionSeqHelper.type());
    }

    public static AttributeDescription[] extract(Any a) {
        return AttrDescriptionSeqHelper.read(a.create_input_stream());
    }

    public static synchronized TypeCode type() {
        if (__typeCode == null) {
            __typeCode = AttributeDescriptionHelper.type();
            __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
            __typeCode = ORB.init().create_alias_tc(AttrDescriptionSeqHelper.id(), "AttrDescriptionSeq", __typeCode);
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static AttributeDescription[] read(InputStream istream) {
        AttributeDescription[] value = null;
        int _len0 = istream.read_long();
        value = new AttributeDescription[_len0];
        for (int _o1 = 0; _o1 < value.length; ++_o1) {
            value[_o1] = AttributeDescriptionHelper.read(istream);
        }
        return value;
    }

    public static void write(OutputStream ostream, AttributeDescription[] value) {
        ostream.write_long(value.length);
        for (int _i0 = 0; _i0 < value.length; ++_i0) {
            AttributeDescriptionHelper.write(ostream, value[_i0]);
        }
    }
}

