/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ExtLocalInterfaceDef;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._ExtLocalInterfaceDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class ExtLocalInterfaceDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ExtLocalInterfaceDefHelper> clazz = ExtLocalInterfaceDefHelper.class;
        synchronized (ExtLocalInterfaceDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/ExtLocalInterfaceDef:1.0", "ExtLocalInterfaceDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ExtLocalInterfaceDef s2) {
        any.insert_Object(s2);
    }

    public static ExtLocalInterfaceDef extract(Any any) {
        return ExtLocalInterfaceDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ExtLocalInterfaceDef:1.0";
    }

    public static ExtLocalInterfaceDef read(InputStream in) {
        return ExtLocalInterfaceDefHelper.narrow(in.read_Object(_ExtLocalInterfaceDefStub.class));
    }

    public static void write(OutputStream _out, ExtLocalInterfaceDef s2) {
        _out.write_Object(s2);
    }

    public static ExtLocalInterfaceDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ExtLocalInterfaceDef) {
            return (ExtLocalInterfaceDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/ExtLocalInterfaceDef:1.0")) {
            _ExtLocalInterfaceDefStub stub = new _ExtLocalInterfaceDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ExtLocalInterfaceDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ExtLocalInterfaceDef) {
            return (ExtLocalInterfaceDef)obj;
        }
        _ExtLocalInterfaceDefStub stub = new _ExtLocalInterfaceDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

