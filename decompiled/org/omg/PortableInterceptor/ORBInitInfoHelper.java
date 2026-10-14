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
import org.omg.PortableInterceptor.ORBInitInfo;

public abstract class ORBInitInfoHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ORBInitInfoHelper> clazz = ORBInitInfoHelper.class;
        synchronized (ORBInitInfoHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/PortableInterceptor/ORBInitInfo:1.0", "ORBInitInfo");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ORBInitInfo s2) {
        any.insert_Object(s2);
    }

    public static ORBInitInfo extract(Any any) {
        return ORBInitInfoHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableInterceptor/ORBInitInfo:1.0";
    }

    public static ORBInitInfo read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, ORBInitInfo s2) {
        throw new MARSHAL();
    }

    public static ORBInitInfo narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ORBInitInfo) {
            return (ORBInitInfo)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ORBInitInfo unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ORBInitInfo) {
            return (ORBInitInfo)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

