/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CosNaming.BindingIterator;
import org.omg.CosNaming._BindingIteratorStub;

public abstract class BindingIteratorHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<BindingIteratorHelper> clazz = BindingIteratorHelper.class;
        synchronized (BindingIteratorHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CosNaming/BindingIterator:1.0", "BindingIterator");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, BindingIterator s2) {
        any.insert_Object(s2);
    }

    public static BindingIterator extract(Any any) {
        return BindingIteratorHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CosNaming/BindingIterator:1.0";
    }

    public static BindingIterator read(InputStream in) {
        return BindingIteratorHelper.narrow(in.read_Object(_BindingIteratorStub.class));
    }

    public static void write(OutputStream _out, BindingIterator s2) {
        _out.write_Object(s2);
    }

    public static BindingIterator narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BindingIterator) {
            return (BindingIterator)obj;
        }
        if (obj._is_a("IDL:omg.org/CosNaming/BindingIterator:1.0")) {
            _BindingIteratorStub stub = new _BindingIteratorStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static BindingIterator unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BindingIterator) {
            return (BindingIterator)obj;
        }
        _BindingIteratorStub stub = new _BindingIteratorStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

