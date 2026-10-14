/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Security.MechanismTypeHelper;

public abstract class MechanismTypeListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, String[] s2) {
        any.type(MechanismTypeListHelper.type());
        MechanismTypeListHelper.write(any.create_output_stream(), s2);
    }

    public static String[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return MechanismTypeListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<MechanismTypeListHelper> clazz = MechanismTypeListHelper.class;
        synchronized (MechanismTypeListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(MechanismTypeListHelper.id(), "MechanismTypeList", ORB.init().create_sequence_tc(0, MechanismTypeHelper.type()));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/Security/MechanismTypeList:1.0";
    }

    public static String[] read(InputStream _in) {
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
        String[] _result = new String[_l_result1];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = _in.read_string();
        }
        return _result;
    }

    public static void write(OutputStream _out, String[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            String tmpResult2 = _s[i];
            _out.write_string(tmpResult2);
        }
    }
}

