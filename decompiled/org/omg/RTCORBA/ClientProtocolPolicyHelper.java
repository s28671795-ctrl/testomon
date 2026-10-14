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
import org.omg.RTCORBA.ClientProtocolPolicy;

public abstract class ClientProtocolPolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ClientProtocolPolicyHelper> clazz = ClientProtocolPolicyHelper.class;
        synchronized (ClientProtocolPolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/RTCORBA/ClientProtocolPolicy:1.0", "ClientProtocolPolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ClientProtocolPolicy s2) {
        any.insert_Object(s2);
    }

    public static ClientProtocolPolicy extract(Any any) {
        return ClientProtocolPolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/RTCORBA/ClientProtocolPolicy:1.0";
    }

    public static ClientProtocolPolicy read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, ClientProtocolPolicy s2) {
        throw new MARSHAL();
    }

    public static ClientProtocolPolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ClientProtocolPolicy) {
            return (ClientProtocolPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ClientProtocolPolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ClientProtocolPolicy) {
            return (ClientProtocolPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

