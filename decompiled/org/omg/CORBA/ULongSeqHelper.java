/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ULongSeqHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, int[] s2) {
        any.type(ULongSeqHelper.type());
        ULongSeqHelper.write(any.create_output_stream(), s2);
    }

    public static int[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ULongSeqHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ULongSeqHelper> clazz = ULongSeqHelper.class;
        synchronized (ULongSeqHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ULongSeqHelper.id(), "ULongSeq", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(5))));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ULongSeq:1.0";
    }

    public static int[] read(InputStream _in) {
        int _l_result36 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result36 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result36);
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        int[] _result = new int[_l_result36];
        _in.read_ulong_array(_result, 0, _l_result36);
        return _result;
    }

    public static void write(OutputStream _out, int[] _s) {
        _out.write_long(_s.length);
        _out.write_ulong_array(_s, 0, _s.length);
    }
}

