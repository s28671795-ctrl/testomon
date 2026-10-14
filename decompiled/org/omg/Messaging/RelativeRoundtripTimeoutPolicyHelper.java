/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Messaging.RelativeRoundtripTimeoutPolicy;

public abstract class RelativeRoundtripTimeoutPolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<RelativeRoundtripTimeoutPolicyHelper> clazz = RelativeRoundtripTimeoutPolicyHelper.class;
        synchronized (RelativeRoundtripTimeoutPolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/Messaging/RelativeRoundtripTimeoutPolicy:1.0", "RelativeRoundtripTimeoutPolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, RelativeRoundtripTimeoutPolicy s2) {
        any.insert_Object(s2);
    }

    public static RelativeRoundtripTimeoutPolicy extract(Any any) {
        return RelativeRoundtripTimeoutPolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/Messaging/RelativeRoundtripTimeoutPolicy:1.0";
    }

    public static RelativeRoundtripTimeoutPolicy read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, RelativeRoundtripTimeoutPolicy s2) {
        throw new MARSHAL();
    }

    public static RelativeRoundtripTimeoutPolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof RelativeRoundtripTimeoutPolicy) {
            return (RelativeRoundtripTimeoutPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static RelativeRoundtripTimeoutPolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof RelativeRoundtripTimeoutPolicy) {
            return (RelativeRoundtripTimeoutPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

