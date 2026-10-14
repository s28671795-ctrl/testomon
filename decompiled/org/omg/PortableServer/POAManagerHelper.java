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
import org.omg.PortableServer.POAManager;

public abstract class POAManagerHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<POAManagerHelper> clazz = POAManagerHelper.class;
        synchronized (POAManagerHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/PortableServer/POAManager:1.0", "POAManager");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, POAManager s2) {
        any.insert_Object(s2);
    }

    public static POAManager extract(Any any) {
        return POAManagerHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableServer/POAManager:1.0";
    }

    public static POAManager read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, POAManager s2) {
        throw new MARSHAL();
    }

    public static POAManager narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof POAManager) {
            return (POAManager)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static POAManager unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof POAManager) {
            return (POAManager)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

