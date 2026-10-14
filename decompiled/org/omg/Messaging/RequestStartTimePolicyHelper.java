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
import org.omg.Messaging.RequestStartTimePolicy;

public abstract class RequestStartTimePolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<RequestStartTimePolicyHelper> clazz = RequestStartTimePolicyHelper.class;
        synchronized (RequestStartTimePolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/Messaging/RequestStartTimePolicy:1.0", "RequestStartTimePolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, RequestStartTimePolicy s2) {
        any.insert_Object(s2);
    }

    public static RequestStartTimePolicy extract(Any any) {
        return RequestStartTimePolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/Messaging/RequestStartTimePolicy:1.0";
    }

    public static RequestStartTimePolicy read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, RequestStartTimePolicy s2) {
        throw new MARSHAL();
    }

    public static RequestStartTimePolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof RequestStartTimePolicy) {
            return (RequestStartTimePolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static RequestStartTimePolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof RequestStartTimePolicy) {
            return (RequestStartTimePolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

