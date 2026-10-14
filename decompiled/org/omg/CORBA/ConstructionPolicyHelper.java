/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ConstructionPolicy;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._ConstructionPolicyStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class ConstructionPolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ConstructionPolicyHelper> clazz = ConstructionPolicyHelper.class;
        synchronized (ConstructionPolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/ConstructionPolicy:1.0", "ConstructionPolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ConstructionPolicy s2) {
        any.insert_Object(s2);
    }

    public static ConstructionPolicy extract(Any any) {
        return ConstructionPolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ConstructionPolicy:1.0";
    }

    public static ConstructionPolicy read(InputStream in) {
        return ConstructionPolicyHelper.narrow(in.read_Object(_ConstructionPolicyStub.class));
    }

    public static void write(OutputStream _out, ConstructionPolicy s2) {
        _out.write_Object(s2);
    }

    public static ConstructionPolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ConstructionPolicy) {
            return (ConstructionPolicy)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/ConstructionPolicy:1.0")) {
            _ConstructionPolicyStub stub = new _ConstructionPolicyStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ConstructionPolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ConstructionPolicy) {
            return (ConstructionPolicy)obj;
        }
        _ConstructionPolicyStub stub = new _ConstructionPolicyStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

