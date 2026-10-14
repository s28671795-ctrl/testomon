/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ConstantDef;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._ConstantDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class ConstantDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ConstantDefHelper> clazz = ConstantDefHelper.class;
        synchronized (ConstantDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/ConstantDef:1.0", "ConstantDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ConstantDef s2) {
        any.insert_Object(s2);
    }

    public static ConstantDef extract(Any any) {
        return ConstantDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ConstantDef:1.0";
    }

    public static ConstantDef read(InputStream in) {
        return ConstantDefHelper.narrow(in.read_Object(_ConstantDefStub.class));
    }

    public static void write(OutputStream _out, ConstantDef s2) {
        _out.write_Object(s2);
    }

    public static ConstantDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ConstantDef) {
            return (ConstantDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/ConstantDef:1.0")) {
            _ConstantDefStub stub = new _ConstantDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ConstantDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ConstantDef) {
            return (ConstantDef)obj;
        }
        _ConstantDefStub stub = new _ConstantDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

