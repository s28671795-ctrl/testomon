/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class PrincipalHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, byte[] s2) {
        any.type(PrincipalHelper.type());
        PrincipalHelper.write(any.create_output_stream(), s2);
    }

    public static byte[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return PrincipalHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PrincipalHelper> clazz = PrincipalHelper.class;
        synchronized (PrincipalHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(PrincipalHelper.id(), "Principal", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/GIOP/Principal:1.0";
    }

    public static byte[] read(InputStream _in) {
        int _l_result0 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result0 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result0);
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        byte[] _result = new byte[_l_result0];
        _in.read_octet_array(_result, 0, _l_result0);
        return _result;
    }

    public static void write(OutputStream _out, byte[] _s) {
        _out.write_long(_s.length);
        _out.write_octet_array(_s, 0, _s.length);
    }
}

