/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableInterceptor.ORBInitializer;

public abstract class ORBInitializerHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ORBInitializerHelper> clazz = ORBInitializerHelper.class;
        synchronized (ORBInitializerHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/PortableInterceptor/ORBInitializer:1.0", "ORBInitializer");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ORBInitializer s2) {
        any.insert_Object(s2);
    }

    public static ORBInitializer extract(Any any) {
        return ORBInitializerHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableInterceptor/ORBInitializer:1.0";
    }

    public static ORBInitializer read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, ORBInitializer s2) {
        throw new MARSHAL();
    }

    public static ORBInitializer narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ORBInitializer) {
            return (ORBInitializer)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ORBInitializer unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ORBInitializer) {
            return (ORBInitializer)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

