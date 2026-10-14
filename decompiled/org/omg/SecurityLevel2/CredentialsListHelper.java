/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.SecurityLevel2.Credentials;
import org.omg.SecurityLevel2.CredentialsHelper;

public abstract class CredentialsListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, Credentials[] s2) {
        any.type(CredentialsListHelper.type());
        CredentialsListHelper.write(any.create_output_stream(), s2);
    }

    public static Credentials[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return CredentialsListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CredentialsListHelper> clazz = CredentialsListHelper.class;
        synchronized (CredentialsListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(CredentialsListHelper.id(), "CredentialsList", ORB.init().create_sequence_tc(0, ORB.init().create_interface_tc("IDL:omg.org/SecurityLevel2/Credentials:1.0", "Credentials")));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/SecurityLevel2/CredentialsList:1.0";
    }

    public static Credentials[] read(InputStream _in) {
        int _l_result0 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result0 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result0);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        Credentials[] _result = new Credentials[_l_result0];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = CredentialsHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, Credentials[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            CredentialsHelper.write(_out, _s[i]);
        }
    }
}

