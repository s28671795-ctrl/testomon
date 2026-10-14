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
import org.omg.PortableServer.ImplicitActivationPolicy;

public abstract class ImplicitActivationPolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ImplicitActivationPolicyHelper> clazz = ImplicitActivationPolicyHelper.class;
        synchronized (ImplicitActivationPolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/PortableServer/ImplicitActivationPolicy:1.0", "ImplicitActivationPolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ImplicitActivationPolicy s2) {
        any.insert_Object(s2);
    }

    public static ImplicitActivationPolicy extract(Any any) {
        return ImplicitActivationPolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableServer/ImplicitActivationPolicy:1.0";
    }

    public static ImplicitActivationPolicy read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, ImplicitActivationPolicy s2) {
        throw new MARSHAL();
    }

    public static ImplicitActivationPolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ImplicitActivationPolicy) {
            return (ImplicitActivationPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ImplicitActivationPolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ImplicitActivationPolicy) {
            return (ImplicitActivationPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

