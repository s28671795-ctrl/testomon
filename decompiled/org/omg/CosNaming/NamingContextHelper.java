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
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming._NamingContextStub;

public abstract class NamingContextHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<NamingContextHelper> clazz = NamingContextHelper.class;
        synchronized (NamingContextHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CosNaming/NamingContext:1.0", "NamingContext");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, NamingContext s2) {
        any.insert_Object(s2);
    }

    public static NamingContext extract(Any any) {
        return NamingContextHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CosNaming/NamingContext:1.0";
    }

    public static NamingContext read(InputStream in) {
        return NamingContextHelper.narrow(in.read_Object(_NamingContextStub.class));
    }

    public static void write(OutputStream _out, NamingContext s2) {
        _out.write_Object(s2);
    }

    public static NamingContext narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof NamingContext) {
            return (NamingContext)obj;
        }
        if (obj._is_a("IDL:omg.org/CosNaming/NamingContext:1.0")) {
            _NamingContextStub stub = new _NamingContextStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static NamingContext unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof NamingContext) {
            return (NamingContext)obj;
        }
        _NamingContextStub stub = new _NamingContextStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

