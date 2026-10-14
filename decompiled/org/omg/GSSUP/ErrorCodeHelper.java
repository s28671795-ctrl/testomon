/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GSSUP;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ErrorCodeHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, int s2) {
        any.insert_ulong(s2);
    }

    public static int extract(Any any) {
        return any.extract_ulong();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ErrorCodeHelper> clazz = ErrorCodeHelper.class;
        synchronized (ErrorCodeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ErrorCodeHelper.id(), "ErrorCode", ORB.init().get_primitive_tc(TCKind.from_int(5)));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/GSSUP/ErrorCode:1.0";
    }

    public static int read(InputStream _in) {
        int _result = _in.read_ulong();
        return _result;
    }

    public static void write(OutputStream _out, int _s) {
        _out.write_ulong(_s);
    }
}

