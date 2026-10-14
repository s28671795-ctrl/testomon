/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.IRObject;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._IRObjectStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class IRObjectHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<IRObjectHelper> clazz = IRObjectHelper.class;
        synchronized (IRObjectHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/IRObject:1.0", "IRObject");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, IRObject s2) {
        any.insert_Object(s2);
    }

    public static IRObject extract(Any any) {
        return IRObjectHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/IRObject:1.0";
    }

    public static IRObject read(InputStream in) {
        return IRObjectHelper.narrow(in.read_Object(_IRObjectStub.class));
    }

    public static void write(OutputStream _out, IRObject s2) {
        _out.write_Object(s2);
    }

    public static IRObject narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof IRObject) {
            return (IRObject)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/IRObject:1.0")) {
            _IRObjectStub stub = new _IRObjectStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static IRObject unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof IRObject) {
            return (IRObject)obj;
        }
        _IRObjectStub stub = new _IRObjectStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

