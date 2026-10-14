/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTPortableServer;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.RTPortableServer.POA;

public abstract class POAHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<POAHelper> clazz = POAHelper.class;
        synchronized (POAHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/RTPortableServer/POA:1.0", "POA");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, POA s2) {
        any.insert_Object(s2);
    }

    public static POA extract(Any any) {
        return POAHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/RTPortableServer/POA:1.0";
    }

    public static POA read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, POA s2) {
        throw new MARSHAL();
    }

    public static POA narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof POA) {
            return (POA)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static POA unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof POA) {
            return (POA)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

