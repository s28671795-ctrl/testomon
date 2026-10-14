/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.InterfaceDefHelper;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class InterfaceDefSeqHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, InterfaceDef[] s2) {
        any.type(InterfaceDefSeqHelper.type());
        InterfaceDefSeqHelper.write(any.create_output_stream(), s2);
    }

    public static InterfaceDef[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return InterfaceDefSeqHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InterfaceDefSeqHelper> clazz = InterfaceDefSeqHelper.class;
        synchronized (InterfaceDefSeqHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(InterfaceDefSeqHelper.id(), "InterfaceDefSeq", ORB.init().create_sequence_tc(0, ORB.init().create_interface_tc("IDL:omg.org/CORBA/InterfaceDef:1.0", "InterfaceDef")));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CORBA/InterfaceDefSeq:1.0";
    }

    public static InterfaceDef[] read(InputStream _in) {
        int _l_result4 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result4 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result4);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        InterfaceDef[] _result = new InterfaceDef[_l_result4];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = InterfaceDefHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, InterfaceDef[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            InterfaceDefHelper.write(_out, _s[i]);
        }
    }
}

