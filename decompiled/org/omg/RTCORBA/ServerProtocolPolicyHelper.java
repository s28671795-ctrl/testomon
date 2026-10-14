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
import org.omg.RTCORBA.ServerProtocolPolicy;

public abstract class ServerProtocolPolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ServerProtocolPolicyHelper> clazz = ServerProtocolPolicyHelper.class;
        synchronized (ServerProtocolPolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/RTCORBA/ServerProtocolPolicy:1.0", "ServerProtocolPolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ServerProtocolPolicy s2) {
        any.insert_Object(s2);
    }

    public static ServerProtocolPolicy extract(Any any) {
        return ServerProtocolPolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/RTCORBA/ServerProtocolPolicy:1.0";
    }

    public static ServerProtocolPolicy read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, ServerProtocolPolicy s2) {
        throw new MARSHAL();
    }

    public static ServerProtocolPolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ServerProtocolPolicy) {
            return (ServerProtocolPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ServerProtocolPolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ServerProtocolPolicy) {
            return (ServerProtocolPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

