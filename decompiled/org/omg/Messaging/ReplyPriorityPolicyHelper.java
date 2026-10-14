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
import org.omg.Messaging.ReplyPriorityPolicy;

public abstract class ReplyPriorityPolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ReplyPriorityPolicyHelper> clazz = ReplyPriorityPolicyHelper.class;
        synchronized (ReplyPriorityPolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/Messaging/ReplyPriorityPolicy:1.0", "ReplyPriorityPolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ReplyPriorityPolicy s2) {
        any.insert_Object(s2);
    }

    public static ReplyPriorityPolicy extract(Any any) {
        return ReplyPriorityPolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/Messaging/ReplyPriorityPolicy:1.0";
    }

    public static ReplyPriorityPolicy read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, ReplyPriorityPolicy s2) {
        throw new MARSHAL();
    }

    public static ReplyPriorityPolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ReplyPriorityPolicy) {
            return (ReplyPriorityPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ReplyPriorityPolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ReplyPriorityPolicy) {
            return (ReplyPriorityPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

