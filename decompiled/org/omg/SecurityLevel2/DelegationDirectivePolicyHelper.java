/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.SecurityLevel2.DelegationDirectivePolicy;

public abstract class DelegationDirectivePolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<DelegationDirectivePolicyHelper> clazz = DelegationDirectivePolicyHelper.class;
        synchronized (DelegationDirectivePolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/SecurityLevel2/DelegationDirectivePolicy:1.0", "DelegationDirectivePolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, DelegationDirectivePolicy s2) {
        any.insert_Object(s2);
    }

    public static DelegationDirectivePolicy extract(Any any) {
        return DelegationDirectivePolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/SecurityLevel2/DelegationDirectivePolicy:1.0";
    }

    public static DelegationDirectivePolicy read(InputStream in) {
        return DelegationDirectivePolicyHelper.narrow(in.read_Object());
    }

    public static void write(OutputStream _out, DelegationDirectivePolicy s2) {
        _out.write_Object(s2);
    }

    public static DelegationDirectivePolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof DelegationDirectivePolicy) {
            return (DelegationDirectivePolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static DelegationDirectivePolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof DelegationDirectivePolicy) {
            return (DelegationDirectivePolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

