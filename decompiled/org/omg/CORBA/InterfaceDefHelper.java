/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._InterfaceDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class InterfaceDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InterfaceDefHelper> clazz = InterfaceDefHelper.class;
        synchronized (InterfaceDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/InterfaceDef:1.0", "InterfaceDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InterfaceDef s2) {
        any.insert_Object(s2);
    }

    public static InterfaceDef extract(Any any) {
        return InterfaceDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/InterfaceDef:1.0";
    }

    public static InterfaceDef read(InputStream in) {
        return InterfaceDefHelper.narrow(in.read_Object(_InterfaceDefStub.class));
    }

    public static void write(OutputStream _out, InterfaceDef s2) {
        _out.write_Object(s2);
    }

    public static InterfaceDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof InterfaceDef) {
            return (InterfaceDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/InterfaceDef:1.0")) {
            _InterfaceDefStub stub = new _InterfaceDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static InterfaceDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof InterfaceDef) {
            return (InterfaceDef)obj;
        }
        _InterfaceDefStub stub = new _InterfaceDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

