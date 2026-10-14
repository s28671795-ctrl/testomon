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
import org.omg.PortableServer.ServantRetentionPolicy;

public abstract class ServantRetentionPolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ServantRetentionPolicyHelper> clazz = ServantRetentionPolicyHelper.class;
        synchronized (ServantRetentionPolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/PortableServer/ServantRetentionPolicy:1.0", "ServantRetentionPolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ServantRetentionPolicy s2) {
        any.insert_Object(s2);
    }

    public static ServantRetentionPolicy extract(Any any) {
        return ServantRetentionPolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableServer/ServantRetentionPolicy:1.0";
    }

    public static ServantRetentionPolicy read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, ServantRetentionPolicy s2) {
        throw new MARSHAL();
    }

    public static ServantRetentionPolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ServantRetentionPolicy) {
            return (ServantRetentionPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ServantRetentionPolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ServantRetentionPolicy) {
            return (ServantRetentionPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

