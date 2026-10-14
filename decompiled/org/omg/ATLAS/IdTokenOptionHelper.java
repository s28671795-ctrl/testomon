/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CSI.IdentityToken;
import org.omg.CSI.IdentityTokenHelper;

public abstract class IdTokenOptionHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, IdentityToken[] s2) {
        any.type(IdTokenOptionHelper.type());
        IdTokenOptionHelper.write(any.create_output_stream(), s2);
    }

    public static IdentityToken[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return IdTokenOptionHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<IdTokenOptionHelper> clazz = IdTokenOptionHelper.class;
        synchronized (IdTokenOptionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(IdTokenOptionHelper.id(), "IdTokenOption", ORB.init().create_sequence_tc(1, IdentityTokenHelper.type()));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/ATLAS/IdTokenOption:1.0";
    }

    public static IdentityToken[] read(InputStream _in) {
        int _l_result1 = _in.read_long();
        if (_l_result1 > 1) {
            throw new MARSHAL("Sequence length incorrect!");
        }
        try {
            int x = _in.available();
            if (x > 0 && _l_result1 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result1);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        IdentityToken[] _result = new IdentityToken[_l_result1];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = IdentityTokenHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, IdentityToken[] _s) {
        if (_s.length > 1) {
            throw new MARSHAL("Incorrect sequence length");
        }
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            IdentityTokenHelper.write(_out, _s[i]);
        }
    }
}

