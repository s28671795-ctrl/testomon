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
import org.omg.Messaging.RequestEndTimePolicy;

public abstract class RequestEndTimePolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<RequestEndTimePolicyHelper> clazz = RequestEndTimePolicyHelper.class;
        synchronized (RequestEndTimePolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/Messaging/RequestEndTimePolicy:1.0", "RequestEndTimePolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, RequestEndTimePolicy s2) {
        any.insert_Object(s2);
    }

    public static RequestEndTimePolicy extract(Any any) {
        return RequestEndTimePolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/Messaging/RequestEndTimePolicy:1.0";
    }

    public static RequestEndTimePolicy read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, RequestEndTimePolicy s2) {
        throw new MARSHAL();
    }

    public static RequestEndTimePolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof RequestEndTimePolicy) {
            return (RequestEndTimePolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static RequestEndTimePolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof RequestEndTimePolicy) {
            return (RequestEndTimePolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

