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
import org.omg.PortableServer.ServantActivator;

public abstract class ServantActivatorHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ServantActivatorHelper> clazz = ServantActivatorHelper.class;
        synchronized (ServantActivatorHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/PortableServer/ServantActivator:1.0", "ServantActivator");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ServantActivator s2) {
        any.insert_Object(s2);
    }

    public static ServantActivator extract(Any any) {
        return ServantActivatorHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableServer/ServantActivator:1.0";
    }

    public static ServantActivator read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, ServantActivator s2) {
        throw new MARSHAL();
    }

    public static ServantActivator narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ServantActivator) {
            return (ServantActivator)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ServantActivator unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ServantActivator) {
            return (ServantActivator)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

