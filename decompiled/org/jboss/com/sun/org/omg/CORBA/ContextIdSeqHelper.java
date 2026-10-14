/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.jboss.com.sun.org.omg.CORBA.ContextIdentifierHelper;
import org.jboss.com.sun.org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class ContextIdSeqHelper {
    private static String _id = "IDL:omg.org/CORBA/ContextIdSeq:1.0";
    private static TypeCode __typeCode = null;

    public static void insert(Any a, String[] that) {
        OutputStream out = a.create_output_stream();
        a.type(ContextIdSeqHelper.type());
        ContextIdSeqHelper.write(out, that);
        a.read_value(out.create_input_stream(), ContextIdSeqHelper.type());
    }

    public static String[] extract(Any a) {
        return ContextIdSeqHelper.read(a.create_input_stream());
    }

    public static synchronized TypeCode type() {
        if (__typeCode == null) {
            __typeCode = ORB.init().create_string_tc(0);
            __typeCode = ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", __typeCode);
            __typeCode = ORB.init().create_alias_tc(ContextIdentifierHelper.id(), "ContextIdentifier", __typeCode);
            __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
            __typeCode = ORB.init().create_alias_tc(ContextIdSeqHelper.id(), "ContextIdSeq", __typeCode);
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static String[] read(InputStream istream) {
        String[] value = null;
        int _len0 = istream.read_long();
        value = new String[_len0];
        for (int _o1 = 0; _o1 < value.length; ++_o1) {
            value[_o1] = ContextIdentifierHelper.read(istream);
        }
        return value;
    }

    public static void write(OutputStream ostream, String[] value) {
        ostream.write_long(value.length);
        for (int _i0 = 0; _i0 < value.length; ++_i0) {
            ContextIdentifierHelper.write(ostream, value[_i0]);
        }
    }
}

