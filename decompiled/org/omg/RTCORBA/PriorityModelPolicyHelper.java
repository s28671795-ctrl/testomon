/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.RTCORBA.PriorityModelPolicy;

public abstract class PriorityModelPolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PriorityModelPolicyHelper> clazz = PriorityModelPolicyHelper.class;
        synchronized (PriorityModelPolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/RTCORBA/PriorityModelPolicy:1.0", "PriorityModelPolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, PriorityModelPolicy s2) {
        any.insert_Object(s2);
    }

    public static PriorityModelPolicy extract(Any any) {
        return PriorityModelPolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/RTCORBA/PriorityModelPolicy:1.0";
    }

    public static PriorityModelPolicy read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, PriorityModelPolicy s2) {
        throw new MARSHAL();
    }

    public static PriorityModelPolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof PriorityModelPolicy) {
            return (PriorityModelPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static PriorityModelPolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof PriorityModelPolicy) {
            return (PriorityModelPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

