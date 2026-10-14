/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.OperationDef;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._OperationDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class OperationDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<OperationDefHelper> clazz = OperationDefHelper.class;
        synchronized (OperationDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/OperationDef:1.0", "OperationDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, OperationDef s2) {
        any.insert_Object(s2);
    }

    public static OperationDef extract(Any any) {
        return OperationDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/OperationDef:1.0";
    }

    public static OperationDef read(InputStream in) {
        return OperationDefHelper.narrow(in.read_Object(_OperationDefStub.class));
    }

    public static void write(OutputStream _out, OperationDef s2) {
        _out.write_Object(s2);
    }

    public static OperationDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof OperationDef) {
            return (OperationDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/OperationDef:1.0")) {
            _OperationDefStub stub = new _OperationDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static OperationDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof OperationDef) {
            return (OperationDef)obj;
        }
        _OperationDefStub stub = new _OperationDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

