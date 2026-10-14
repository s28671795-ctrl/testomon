/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AbstractInterfaceDef;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._AbstractInterfaceDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class AbstractInterfaceDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AbstractInterfaceDefHelper> clazz = AbstractInterfaceDefHelper.class;
        synchronized (AbstractInterfaceDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/AbstractInterfaceDef:1.0", "AbstractInterfaceDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AbstractInterfaceDef s2) {
        any.insert_Object(s2);
    }

    public static AbstractInterfaceDef extract(Any any) {
        return AbstractInterfaceDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/AbstractInterfaceDef:1.0";
    }

    public static AbstractInterfaceDef read(InputStream in) {
        return AbstractInterfaceDefHelper.narrow(in.read_Object(_AbstractInterfaceDefStub.class));
    }

    public static void write(OutputStream _out, AbstractInterfaceDef s2) {
        _out.write_Object(s2);
    }

    public static AbstractInterfaceDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AbstractInterfaceDef) {
            return (AbstractInterfaceDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/AbstractInterfaceDef:1.0")) {
            _AbstractInterfaceDefStub stub = new _AbstractInterfaceDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static AbstractInterfaceDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AbstractInterfaceDef) {
            return (AbstractInterfaceDef)obj;
        }
        _AbstractInterfaceDefStub stub = new _AbstractInterfaceDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

