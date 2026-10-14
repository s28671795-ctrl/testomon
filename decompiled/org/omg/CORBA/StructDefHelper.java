/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.StructDef;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._StructDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class StructDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<StructDefHelper> clazz = StructDefHelper.class;
        synchronized (StructDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/StructDef:1.0", "StructDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, StructDef s2) {
        any.insert_Object(s2);
    }

    public static StructDef extract(Any any) {
        return StructDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/StructDef:1.0";
    }

    public static StructDef read(InputStream in) {
        return StructDefHelper.narrow(in.read_Object(_StructDefStub.class));
    }

    public static void write(OutputStream _out, StructDef s2) {
        _out.write_Object(s2);
    }

    public static StructDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof StructDef) {
            return (StructDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/StructDef:1.0")) {
            _StructDefStub stub = new _StructDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static StructDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof StructDef) {
            return (StructDef)obj;
        }
        _StructDefStub stub = new _StructDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

