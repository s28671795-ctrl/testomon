/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.DynamicAny.DynAny;
import org.omg.DynamicAny.DynAnyHelper;

public abstract class DynAnySeqHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, DynAny[] s2) {
        any.type(DynAnySeqHelper.type());
        DynAnySeqHelper.write(any.create_output_stream(), s2);
    }

    public static DynAny[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return DynAnySeqHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<DynAnySeqHelper> clazz = DynAnySeqHelper.class;
        synchronized (DynAnySeqHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(DynAnySeqHelper.id(), "DynAnySeq", ORB.init().create_sequence_tc(0, ORB.init().create_local_interface_tc("IDL:omg.org/DynamicAny/DynAny:1.0", "DynAny")));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/DynamicAny/DynAnySeq:1.0";
    }

    public static DynAny[] read(InputStream _in) {
        int _l_result3 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result3 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result3);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        DynAny[] _result = new DynAny[_l_result3];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = DynAnyHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, DynAny[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            DynAnyHelper.write(_out, _s[i]);
        }
    }
}

