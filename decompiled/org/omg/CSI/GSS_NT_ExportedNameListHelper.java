/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CSI.GSS_NT_ExportedNameHelper;

public abstract class GSS_NT_ExportedNameListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, byte[][] s2) {
        any.type(GSS_NT_ExportedNameListHelper.type());
        GSS_NT_ExportedNameListHelper.write(any.create_output_stream(), s2);
    }

    public static byte[][] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return GSS_NT_ExportedNameListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<GSS_NT_ExportedNameListHelper> clazz = GSS_NT_ExportedNameListHelper.class;
        synchronized (GSS_NT_ExportedNameListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(GSS_NT_ExportedNameListHelper.id(), "GSS_NT_ExportedNameList", ORB.init().create_sequence_tc(0, GSS_NT_ExportedNameHelper.type()));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CSI/GSS_NT_ExportedNameList:1.0";
    }

    public static byte[][] read(InputStream _in) {
        int _l_result7 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result7 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result7);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        byte[][] _result = new byte[_l_result7][];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = GSS_NT_ExportedNameHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, byte[][] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            GSS_NT_ExportedNameHelper.write(_out, _s[i]);
        }
    }
}

