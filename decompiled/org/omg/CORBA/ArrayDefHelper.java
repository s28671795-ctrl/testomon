/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.ArrayDef;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._ArrayDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class ArrayDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ArrayDefHelper> clazz = ArrayDefHelper.class;
        synchronized (ArrayDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/ArrayDef:1.0", "ArrayDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ArrayDef s2) {
        any.insert_Object(s2);
    }

    public static ArrayDef extract(Any any) {
        return ArrayDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ArrayDef:1.0";
    }

    public static ArrayDef read(InputStream in) {
        return ArrayDefHelper.narrow(in.read_Object(_ArrayDefStub.class));
    }

    public static void write(OutputStream _out, ArrayDef s2) {
        _out.write_Object(s2);
    }

    public static ArrayDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ArrayDef) {
            return (ArrayDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/ArrayDef:1.0")) {
            _ArrayDefStub stub = new _ArrayDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ArrayDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ArrayDef) {
            return (ArrayDef)obj;
        }
        _ArrayDefStub stub = new _ArrayDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

