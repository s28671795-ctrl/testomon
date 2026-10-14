/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._PolicyStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class PolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PolicyHelper> clazz = PolicyHelper.class;
        synchronized (PolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/Policy:1.0", "Policy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, Policy s2) {
        any.insert_Object(s2);
    }

    public static Policy extract(Any any) {
        return PolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/Policy:1.0";
    }

    public static Policy read(InputStream in) {
        return PolicyHelper.narrow(in.read_Object(_PolicyStub.class));
    }

    public static void write(OutputStream _out, Policy s2) {
        _out.write_Object(s2);
    }

    public static Policy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Policy) {
            return (Policy)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/Policy:1.0")) {
            _PolicyStub stub = new _PolicyStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static Policy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Policy) {
            return (Policy)obj;
        }
        _PolicyStub stub = new _PolicyStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

