/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.jboss.com.sun.org.omg.CORBA.ValueMemberHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class ValueMemberSeqHelper {
    private static String _id = "IDL:omg.org/CORBA/ValueMemberSeq:1.0";
    private static TypeCode __typeCode = null;

    public static void insert(Any a, ValueMember[] that) {
        OutputStream out = a.create_output_stream();
        a.type(ValueMemberSeqHelper.type());
        ValueMemberSeqHelper.write(out, that);
        a.read_value(out.create_input_stream(), ValueMemberSeqHelper.type());
    }

    public static ValueMember[] extract(Any a) {
        return ValueMemberSeqHelper.read(a.create_input_stream());
    }

    public static synchronized TypeCode type() {
        if (__typeCode == null) {
            __typeCode = ValueMemberHelper.type();
            __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
            __typeCode = ORB.init().create_alias_tc(ValueMemberSeqHelper.id(), "ValueMemberSeq", __typeCode);
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static ValueMember[] read(InputStream istream) {
        ValueMember[] value = null;
        int _len0 = istream.read_long();
        value = new ValueMember[_len0];
        for (int _o1 = 0; _o1 < value.length; ++_o1) {
            value[_o1] = ValueMemberHelper.read(istream);
        }
        return value;
    }

    public static void write(OutputStream ostream, ValueMember[] value) {
        ostream.write_long(value.length);
        for (int _i0 = 0; _i0 < value.length; ++_i0) {
            ValueMemberHelper.write(ostream, value[_i0]);
        }
    }
}

