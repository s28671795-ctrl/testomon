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
import org.omg.SecurityAdmin.AccessPolicy;

public abstract class AccessPolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AccessPolicyHelper> clazz = AccessPolicyHelper.class;
        synchronized (AccessPolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/SecurityAdmin/AccessPolicy:1.0", "AccessPolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AccessPolicy s2) {
        any.insert_Object(s2);
    }

    public static AccessPolicy extract(Any any) {
        return AccessPolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/SecurityAdmin/AccessPolicy:1.0";
    }

    public static AccessPolicy read(InputStream in) {
        return AccessPolicyHelper.narrow(in.read_Object());
    }

    public static void write(OutputStream _out, AccessPolicy s2) {
        _out.write_Object(s2);
    }

    public static AccessPolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AccessPolicy) {
            return (AccessPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static AccessPolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AccessPolicy) {
            return (AccessPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

