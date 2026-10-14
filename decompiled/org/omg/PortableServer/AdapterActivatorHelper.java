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
import org.omg.PortableServer.AdapterActivator;

public abstract class AdapterActivatorHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AdapterActivatorHelper> clazz = AdapterActivatorHelper.class;
        synchronized (AdapterActivatorHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/PortableServer/AdapterActivator:1.0", "AdapterActivator");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AdapterActivator s2) {
        any.insert_Object(s2);
    }

    public static AdapterActivator extract(Any any) {
        return AdapterActivatorHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableServer/AdapterActivator:1.0";
    }

    public static AdapterActivator read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, AdapterActivator s2) {
        throw new MARSHAL();
    }

    public static AdapterActivator narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AdapterActivator) {
            return (AdapterActivator)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static AdapterActivator unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AdapterActivator) {
            return (AdapterActivator)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

