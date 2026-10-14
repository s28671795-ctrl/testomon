/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueMemberDef;
import org.omg.CORBA._ValueMemberDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class ValueMemberDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ValueMemberDefHelper> clazz = ValueMemberDefHelper.class;
        synchronized (ValueMemberDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/ValueMemberDef:1.0", "ValueMemberDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ValueMemberDef s2) {
        any.insert_Object(s2);
    }

    public static ValueMemberDef extract(Any any) {
        return ValueMemberDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ValueMemberDef:1.0";
    }

    public static ValueMemberDef read(InputStream in) {
        return ValueMemberDefHelper.narrow(in.read_Object(_ValueMemberDefStub.class));
    }

    public static void write(OutputStream _out, ValueMemberDef s2) {
        _out.write_Object(s2);
    }

    public static ValueMemberDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ValueMemberDef) {
            return (ValueMemberDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/ValueMemberDef:1.0")) {
            _ValueMemberDefStub stub = new _ValueMemberDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ValueMemberDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ValueMemberDef) {
            return (ValueMemberDef)obj;
        }
        _ValueMemberDefStub stub = new _ValueMemberDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

