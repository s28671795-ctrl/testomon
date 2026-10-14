/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ExtInterfaceDef;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._ExtInterfaceDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class ExtInterfaceDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ExtInterfaceDefHelper> clazz = ExtInterfaceDefHelper.class;
        synchronized (ExtInterfaceDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/ExtInterfaceDef:1.0", "ExtInterfaceDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ExtInterfaceDef s2) {
        any.insert_Object(s2);
    }

    public static ExtInterfaceDef extract(Any any) {
        return ExtInterfaceDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ExtInterfaceDef:1.0";
    }

    public static ExtInterfaceDef read(InputStream in) {
        return ExtInterfaceDefHelper.narrow(in.read_Object(_ExtInterfaceDefStub.class));
    }

    public static void write(OutputStream _out, ExtInterfaceDef s2) {
        _out.write_Object(s2);
    }

    public static ExtInterfaceDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ExtInterfaceDef) {
            return (ExtInterfaceDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/ExtInterfaceDef:1.0")) {
            _ExtInterfaceDefStub stub = new _ExtInterfaceDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ExtInterfaceDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ExtInterfaceDef) {
            return (ExtInterfaceDef)obj;
        }
        _ExtInterfaceDefStub stub = new _ExtInterfaceDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

