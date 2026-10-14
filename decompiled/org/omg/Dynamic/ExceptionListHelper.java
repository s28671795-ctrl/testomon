/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Dynamic;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ExceptionListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, TypeCode[] s2) {
        any.type(ExceptionListHelper.type());
        ExceptionListHelper.write(any.create_output_stream(), s2);
    }

    public static TypeCode[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ExceptionListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ExceptionListHelper> clazz = ExceptionListHelper.class;
        synchronized (ExceptionListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ExceptionListHelper.id(), "ExceptionList", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.tk_TypeCode)));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/Dynamic/ExceptionList:1.0";
    }

    public static TypeCode[] read(InputStream _in) {
        int _l_result1 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result1 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result1);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        TypeCode[] _result = new TypeCode[_l_result1];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = _in.read_TypeCode();
        }
        return _result;
    }

    public static void write(OutputStream _out, TypeCode[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            _out.write_TypeCode(_s[i]);
        }
    }
}

