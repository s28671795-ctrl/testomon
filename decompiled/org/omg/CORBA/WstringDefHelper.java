/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.WstringDef;
import org.omg.CORBA._WstringDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class WstringDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<WstringDefHelper> clazz = WstringDefHelper.class;
        synchronized (WstringDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/WstringDef:1.0", "WstringDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, WstringDef s2) {
        any.insert_Object(s2);
    }

    public static WstringDef extract(Any any) {
        return WstringDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/WstringDef:1.0";
    }

    public static WstringDef read(InputStream in) {
        return WstringDefHelper.narrow(in.read_Object(_WstringDefStub.class));
    }

    public static void write(OutputStream _out, WstringDef s2) {
        _out.write_Object(s2);
    }

    public static WstringDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof WstringDef) {
            return (WstringDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/WstringDef:1.0")) {
            _WstringDefStub stub = new _WstringDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static WstringDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof WstringDef) {
            return (WstringDef)obj;
        }
        _WstringDefStub stub = new _WstringDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

