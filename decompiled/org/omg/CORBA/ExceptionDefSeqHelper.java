/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.ExceptionDefHelper;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ExceptionDefSeqHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, ExceptionDef[] s2) {
        any.type(ExceptionDefSeqHelper.type());
        ExceptionDefSeqHelper.write(any.create_output_stream(), s2);
    }

    public static ExceptionDef[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ExceptionDefSeqHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ExceptionDefSeqHelper> clazz = ExceptionDefSeqHelper.class;
        synchronized (ExceptionDefSeqHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ExceptionDefSeqHelper.id(), "ExceptionDefSeq", ORB.init().create_sequence_tc(0, ORB.init().create_interface_tc("IDL:omg.org/CORBA/ExceptionDef:1.0", "ExceptionDef")));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ExceptionDefSeq:1.0";
    }

    public static ExceptionDef[] read(InputStream _in) {
        int _l_result22 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result22 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result22);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        ExceptionDef[] _result = new ExceptionDef[_l_result22];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = ExceptionDefHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, ExceptionDef[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            ExceptionDefHelper.write(_out, _s[i]);
        }
    }
}

