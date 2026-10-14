/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.PolicyCurrent;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class PolicyCurrentHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PolicyCurrentHelper> clazz = PolicyCurrentHelper.class;
        synchronized (PolicyCurrentHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/CORBA/PolicyCurrent:1.0", "PolicyCurrent");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, PolicyCurrent s2) {
        any.insert_Object(s2);
    }

    public static PolicyCurrent extract(Any any) {
        return PolicyCurrentHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/PolicyCurrent:1.0";
    }

    public static PolicyCurrent read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, PolicyCurrent s2) {
        throw new MARSHAL();
    }

    public static PolicyCurrent narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof PolicyCurrent) {
            return (PolicyCurrent)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static PolicyCurrent unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof PolicyCurrent) {
            return (PolicyCurrent)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

