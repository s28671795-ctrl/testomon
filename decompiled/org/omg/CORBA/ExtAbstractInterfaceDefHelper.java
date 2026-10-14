/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ExtAbstractInterfaceDef;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._ExtAbstractInterfaceDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class ExtAbstractInterfaceDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ExtAbstractInterfaceDefHelper> clazz = ExtAbstractInterfaceDefHelper.class;
        synchronized (ExtAbstractInterfaceDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/ExtAbstractInterfaceDef:1.0", "ExtAbstractInterfaceDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ExtAbstractInterfaceDef s2) {
        any.insert_Object(s2);
    }

    public static ExtAbstractInterfaceDef extract(Any any) {
        return ExtAbstractInterfaceDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ExtAbstractInterfaceDef:1.0";
    }

    public static ExtAbstractInterfaceDef read(InputStream in) {
        return ExtAbstractInterfaceDefHelper.narrow(in.read_Object(_ExtAbstractInterfaceDefStub.class));
    }

    public static void write(OutputStream _out, ExtAbstractInterfaceDef s2) {
        _out.write_Object(s2);
    }

    public static ExtAbstractInterfaceDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ExtAbstractInterfaceDef) {
            return (ExtAbstractInterfaceDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/ExtAbstractInterfaceDef:1.0")) {
            _ExtAbstractInterfaceDefStub stub = new _ExtAbstractInterfaceDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ExtAbstractInterfaceDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ExtAbstractInterfaceDef) {
            return (ExtAbstractInterfaceDef)obj;
        }
        _ExtAbstractInterfaceDefStub stub = new _ExtAbstractInterfaceDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

