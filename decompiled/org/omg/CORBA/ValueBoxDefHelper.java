/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueBoxDef;
import org.omg.CORBA._ValueBoxDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class ValueBoxDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ValueBoxDefHelper> clazz = ValueBoxDefHelper.class;
        synchronized (ValueBoxDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/ValueBoxDef:1.0", "ValueBoxDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ValueBoxDef s2) {
        any.insert_Object(s2);
    }

    public static ValueBoxDef extract(Any any) {
        return ValueBoxDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ValueBoxDef:1.0";
    }

    public static ValueBoxDef read(InputStream in) {
        return ValueBoxDefHelper.narrow(in.read_Object(_ValueBoxDefStub.class));
    }

    public static void write(OutputStream _out, ValueBoxDef s2) {
        _out.write_Object(s2);
    }

    public static ValueBoxDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ValueBoxDef) {
            return (ValueBoxDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/ValueBoxDef:1.0")) {
            _ValueBoxDefStub stub = new _ValueBoxDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ValueBoxDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ValueBoxDef) {
            return (ValueBoxDef)obj;
        }
        _ValueBoxDefStub stub = new _ValueBoxDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

