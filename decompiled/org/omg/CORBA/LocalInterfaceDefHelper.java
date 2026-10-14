/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.LocalInterfaceDef;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._LocalInterfaceDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class LocalInterfaceDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<LocalInterfaceDefHelper> clazz = LocalInterfaceDefHelper.class;
        synchronized (LocalInterfaceDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/LocalInterfaceDef:1.0", "LocalInterfaceDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, LocalInterfaceDef s2) {
        any.insert_Object(s2);
    }

    public static LocalInterfaceDef extract(Any any) {
        return LocalInterfaceDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/LocalInterfaceDef:1.0";
    }

    public static LocalInterfaceDef read(InputStream in) {
        return LocalInterfaceDefHelper.narrow(in.read_Object(_LocalInterfaceDefStub.class));
    }

    public static void write(OutputStream _out, LocalInterfaceDef s2) {
        _out.write_Object(s2);
    }

    public static LocalInterfaceDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof LocalInterfaceDef) {
            return (LocalInterfaceDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/LocalInterfaceDef:1.0")) {
            _LocalInterfaceDefStub stub = new _LocalInterfaceDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static LocalInterfaceDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof LocalInterfaceDef) {
            return (LocalInterfaceDef)obj;
        }
        _LocalInterfaceDefStub stub = new _LocalInterfaceDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

