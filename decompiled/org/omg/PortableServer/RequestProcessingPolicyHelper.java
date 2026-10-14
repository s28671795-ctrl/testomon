/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.RequestProcessingPolicy;

public abstract class RequestProcessingPolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<RequestProcessingPolicyHelper> clazz = RequestProcessingPolicyHelper.class;
        synchronized (RequestProcessingPolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/PortableServer/RequestProcessingPolicy:1.0", "RequestProcessingPolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, RequestProcessingPolicy s2) {
        any.insert_Object(s2);
    }

    public static RequestProcessingPolicy extract(Any any) {
        return RequestProcessingPolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableServer/RequestProcessingPolicy:1.0";
    }

    public static RequestProcessingPolicy read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, RequestProcessingPolicy s2) {
        throw new MARSHAL();
    }

    public static RequestProcessingPolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof RequestProcessingPolicy) {
            return (RequestProcessingPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static RequestProcessingPolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof RequestProcessingPolicy) {
            return (RequestProcessingPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

