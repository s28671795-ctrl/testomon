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

public abstract class ShortSeqHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, short[] s2) {
        any.type(ShortSeqHelper.type());
        ShortSeqHelper.write(any.create_output_stream(), s2);
    }

    public static short[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ShortSeqHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ShortSeqHelper> clazz = ShortSeqHelper.class;
        synchronized (ShortSeqHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ShortSeqHelper.id(), "ShortSeq", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(2))));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ShortSeq:1.0";
    }

    public static short[] read(InputStream _in) {
        int _l_result33 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result33 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result33);
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        short[] _result = new short[_l_result33];
        _in.read_short_array(_result, 0, _l_result33);
        return _result;
    }

    public static void write(OutputStream _out, short[] _s) {
        _out.write_long(_s.length);
        _out.write_short_array(_s, 0, _s.length);
    }
}

