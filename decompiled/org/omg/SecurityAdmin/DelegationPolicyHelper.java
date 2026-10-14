/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityAdmin;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.SecurityAdmin.DelegationPolicy;

public abstract class DelegationPolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<DelegationPolicyHelper> clazz = DelegationPolicyHelper.class;
        synchronized (DelegationPolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/SecurityAdmin/DelegationPolicy:1.0", "DelegationPolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, DelegationPolicy s2) {
        any.insert_Object(s2);
    }

    public static DelegationPolicy extract(Any any) {
        return DelegationPolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/SecurityAdmin/DelegationPolicy:1.0";
    }

    public static DelegationPolicy read(InputStream in) {
        return DelegationPolicyHelper.narrow(in.read_Object());
    }

    public static void write(OutputStream _out, DelegationPolicy s2) {
        _out.write_Object(s2);
    }

    public static DelegationPolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof DelegationPolicy) {
            return (DelegationPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static DelegationPolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof DelegationPolicy) {
            return (DelegationPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

