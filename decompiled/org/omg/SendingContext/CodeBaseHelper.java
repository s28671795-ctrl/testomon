/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SendingContext;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.SendingContext.CodeBase;
import org.omg.SendingContext._CodeBaseStub;

public abstract class CodeBaseHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CodeBaseHelper> clazz = CodeBaseHelper.class;
        synchronized (CodeBaseHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/SendingContext/CodeBase:1.0", "CodeBase");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, CodeBase s2) {
        any.insert_Object(s2);
    }

    public static CodeBase extract(Any any) {
        return CodeBaseHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/SendingContext/CodeBase:1.0";
    }

    public static CodeBase read(InputStream in) {
        return CodeBaseHelper.narrow(in.read_Object(_CodeBaseStub.class));
    }

    public static void write(OutputStream _out, CodeBase s2) {
        _out.write_Object(s2);
    }

    public static CodeBase narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof CodeBase) {
            return (CodeBase)obj;
        }
        if (obj._is_a("IDL:omg.org/SendingContext/CodeBase:1.0")) {
            _CodeBaseStub stub = new _CodeBaseStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static CodeBase unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof CodeBase) {
            return (CodeBase)obj;
        }
        _CodeBaseStub stub = new _CodeBaseStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

