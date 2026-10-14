/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.NativeDef;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._NativeDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class NativeDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<NativeDefHelper> clazz = NativeDefHelper.class;
        synchronized (NativeDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/NativeDef:1.0", "NativeDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, NativeDef s2) {
        any.insert_Object(s2);
    }

    public static NativeDef extract(Any any) {
        return NativeDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/NativeDef:1.0";
    }

    public static NativeDef read(InputStream in) {
        return NativeDefHelper.narrow(in.read_Object(_NativeDefStub.class));
    }

    public static void write(OutputStream _out, NativeDef s2) {
        _out.write_Object(s2);
    }

    public static NativeDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof NativeDef) {
            return (NativeDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/NativeDef:1.0")) {
            _NativeDefStub stub = new _NativeDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static NativeDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof NativeDef) {
            return (NativeDef)obj;
        }
        _NativeDefStub stub = new _NativeDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

