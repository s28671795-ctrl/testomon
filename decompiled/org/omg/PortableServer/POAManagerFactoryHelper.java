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
import org.omg.PortableServer.POAManagerFactory;

public abstract class POAManagerFactoryHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<POAManagerFactoryHelper> clazz = POAManagerFactoryHelper.class;
        synchronized (POAManagerFactoryHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/PortableServer/POAManagerFactory:1.0", "POAManagerFactory");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, POAManagerFactory s2) {
        any.insert_Object(s2);
    }

    public static POAManagerFactory extract(Any any) {
        return POAManagerFactoryHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableServer/POAManagerFactory:1.0";
    }

    public static POAManagerFactory read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, POAManagerFactory s2) {
        throw new MARSHAL();
    }

    public static POAManagerFactory narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof POAManagerFactory) {
            return (POAManagerFactory)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static POAManagerFactory unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof POAManagerFactory) {
            return (POAManagerFactory)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

