/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ExtValueDef;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._ExtValueDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class ExtValueDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ExtValueDefHelper> clazz = ExtValueDefHelper.class;
        synchronized (ExtValueDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/ExtValueDef:1.0", "ExtValueDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ExtValueDef s2) {
        any.insert_Object(s2);
    }

    public static ExtValueDef extract(Any any) {
        return ExtValueDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ExtValueDef:1.0";
    }

    public static ExtValueDef read(InputStream in) {
        return ExtValueDefHelper.narrow(in.read_Object(_ExtValueDefStub.class));
    }

    public static void write(OutputStream _out, ExtValueDef s2) {
        _out.write_Object(s2);
    }

    public static ExtValueDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ExtValueDef) {
            return (ExtValueDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/ExtValueDef:1.0")) {
            _ExtValueDefStub stub = new _ExtValueDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ExtValueDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ExtValueDef) {
            return (ExtValueDef)obj;
        }
        _ExtValueDefStub stub = new _ExtValueDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

