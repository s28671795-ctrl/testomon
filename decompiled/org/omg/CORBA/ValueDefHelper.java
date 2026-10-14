/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueDef;
import org.omg.CORBA._ValueDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class ValueDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ValueDefHelper> clazz = ValueDefHelper.class;
        synchronized (ValueDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/ValueDef:1.0", "ValueDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ValueDef s2) {
        any.insert_Object(s2);
    }

    public static ValueDef extract(Any any) {
        return ValueDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ValueDef:1.0";
    }

    public static ValueDef read(InputStream in) {
        return ValueDefHelper.narrow(in.read_Object(_ValueDefStub.class));
    }

    public static void write(OutputStream _out, ValueDef s2) {
        _out.write_Object(s2);
    }

    public static ValueDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ValueDef) {
            return (ValueDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/ValueDef:1.0")) {
            _ValueDefStub stub = new _ValueDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ValueDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ValueDef) {
            return (ValueDef)obj;
        }
        _ValueDefStub stub = new _ValueDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

