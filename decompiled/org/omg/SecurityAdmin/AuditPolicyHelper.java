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
import org.omg.SecurityAdmin.AuditPolicy;

public abstract class AuditPolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AuditPolicyHelper> clazz = AuditPolicyHelper.class;
        synchronized (AuditPolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/SecurityAdmin/AuditPolicy:1.0", "AuditPolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AuditPolicy s2) {
        any.insert_Object(s2);
    }

    public static AuditPolicy extract(Any any) {
        return AuditPolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/SecurityAdmin/AuditPolicy:1.0";
    }

    public static AuditPolicy read(InputStream in) {
        return AuditPolicyHelper.narrow(in.read_Object());
    }

    public static void write(OutputStream _out, AuditPolicy s2) {
        _out.write_Object(s2);
    }

    public static AuditPolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AuditPolicy) {
            return (AuditPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static AuditPolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AuditPolicy) {
            return (AuditPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

