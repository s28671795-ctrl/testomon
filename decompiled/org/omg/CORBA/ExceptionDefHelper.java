/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._ExceptionDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class ExceptionDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ExceptionDefHelper> clazz = ExceptionDefHelper.class;
        synchronized (ExceptionDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/ExceptionDef:1.0", "ExceptionDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ExceptionDef s2) {
        any.insert_Object(s2);
    }

    public static ExceptionDef extract(Any any) {
        return ExceptionDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ExceptionDef:1.0";
    }

    public static ExceptionDef read(InputStream in) {
        return ExceptionDefHelper.narrow(in.read_Object(_ExceptionDefStub.class));
    }

    public static void write(OutputStream _out, ExceptionDef s2) {
        _out.write_Object(s2);
    }

    public static ExceptionDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ExceptionDef) {
            return (ExceptionDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/ExceptionDef:1.0")) {
            _ExceptionDefStub stub = new _ExceptionDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ExceptionDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ExceptionDef) {
            return (ExceptionDef)obj;
        }
        _ExceptionDefStub stub = new _ExceptionDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

