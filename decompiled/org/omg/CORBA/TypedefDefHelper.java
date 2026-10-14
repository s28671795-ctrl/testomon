/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypedefDef;
import org.omg.CORBA._TypedefDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class TypedefDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<TypedefDefHelper> clazz = TypedefDefHelper.class;
        synchronized (TypedefDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/TypedefDef:1.0", "TypedefDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, TypedefDef s2) {
        any.insert_Object(s2);
    }

    public static TypedefDef extract(Any any) {
        return TypedefDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/TypedefDef:1.0";
    }

    public static TypedefDef read(InputStream in) {
        return TypedefDefHelper.narrow(in.read_Object(_TypedefDefStub.class));
    }

    public static void write(OutputStream _out, TypedefDef s2) {
        _out.write_Object(s2);
    }

    public static TypedefDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof TypedefDef) {
            return (TypedefDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/TypedefDef:1.0")) {
            _TypedefDefStub stub = new _TypedefDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static TypedefDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof TypedefDef) {
            return (TypedefDef)obj;
        }
        _TypedefDefStub stub = new _TypedefDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

