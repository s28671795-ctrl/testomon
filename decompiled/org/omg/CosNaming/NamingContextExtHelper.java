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
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming._NamingContextExtStub;

public abstract class NamingContextExtHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<NamingContextExtHelper> clazz = NamingContextExtHelper.class;
        synchronized (NamingContextExtHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CosNaming/NamingContextExt:1.0", "NamingContextExt");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, NamingContextExt s2) {
        any.insert_Object(s2);
    }

    public static NamingContextExt extract(Any any) {
        return NamingContextExtHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CosNaming/NamingContextExt:1.0";
    }

    public static NamingContextExt read(InputStream in) {
        return NamingContextExtHelper.narrow(in.read_Object(_NamingContextExtStub.class));
    }

    public static void write(OutputStream _out, NamingContextExt s2) {
        _out.write_Object(s2);
    }

    public static NamingContextExt narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof NamingContextExt) {
            return (NamingContextExt)obj;
        }
        if (obj._is_a("IDL:omg.org/CosNaming/NamingContextExt:1.0")) {
            _NamingContextExtStub stub = new _NamingContextExtStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static NamingContextExt unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof NamingContextExt) {
            return (NamingContextExt)obj;
        }
        _NamingContextExtStub stub = new _NamingContextExtStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

