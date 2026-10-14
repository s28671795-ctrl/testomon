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
import org.omg.Messaging.ReplyEndTimePolicy;

public abstract class ReplyEndTimePolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ReplyEndTimePolicyHelper> clazz = ReplyEndTimePolicyHelper.class;
        synchronized (ReplyEndTimePolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/Messaging/ReplyEndTimePolicy:1.0", "ReplyEndTimePolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ReplyEndTimePolicy s2) {
        any.insert_Object(s2);
    }

    public static ReplyEndTimePolicy extract(Any any) {
        return ReplyEndTimePolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/Messaging/ReplyEndTimePolicy:1.0";
    }

    public static ReplyEndTimePolicy read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, ReplyEndTimePolicy s2) {
        throw new MARSHAL();
    }

    public static ReplyEndTimePolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ReplyEndTimePolicy) {
            return (ReplyEndTimePolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ReplyEndTimePolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ReplyEndTimePolicy) {
            return (ReplyEndTimePolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

