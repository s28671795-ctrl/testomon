/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ContextIdHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, long s2) {
        any.insert_ulonglong(s2);
    }

    public static long extract(Any any) {
        return any.extract_ulonglong();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ContextIdHelper> clazz = ContextIdHelper.class;
        synchronized (ContextIdHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ContextIdHelper.id(), "ContextId", ORB.init().get_primitive_tc(TCKind.from_int(24)));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CSI/ContextId:1.0";
    }

    public static long read(InputStream _in) {
        long _result = _in.read_ulonglong();
        return _result;
    }

    public static void write(OutputStream _out, long _s) {
        _out.write_ulonglong(_s);
    }
}

