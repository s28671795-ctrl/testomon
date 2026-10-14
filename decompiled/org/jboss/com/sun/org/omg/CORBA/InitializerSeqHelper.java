/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.jboss.com.sun.org.omg.CORBA.Initializer;
import org.jboss.com.sun.org.omg.CORBA.InitializerHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class InitializerSeqHelper {
    private static String _id = "IDL:omg.org/CORBA/InitializerSeq:1.0";
    private static TypeCode __typeCode = null;

    public static void insert(Any a, Initializer[] that) {
        OutputStream out = a.create_output_stream();
        a.type(InitializerSeqHelper.type());
        InitializerSeqHelper.write(out, that);
        a.read_value(out.create_input_stream(), InitializerSeqHelper.type());
    }

    public static Initializer[] extract(Any a) {
        return InitializerSeqHelper.read(a.create_input_stream());
    }

    public static synchronized TypeCode type() {
        if (__typeCode == null) {
            __typeCode = InitializerHelper.type();
            __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
            __typeCode = ORB.init().create_alias_tc(InitializerSeqHelper.id(), "InitializerSeq", __typeCode);
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static Initializer[] read(InputStream istream) {
        Initializer[] value = null;
        int _len0 = istream.read_long();
        value = new Initializer[_len0];
        for (int _o1 = 0; _o1 < value.length; ++_o1) {
            value[_o1] = InitializerHelper.read(istream);
        }
        return value;
    }

    public static void write(OutputStream ostream, Initializer[] value) {
        ostream.write_long(value.length);
        for (int _i0 = 0; _i0 < value.length; ++_i0) {
            InitializerHelper.write(ostream, value[_i0]);
        }
    }
}

