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
import org.omg.PortableInterceptor.ORBInitInfo_3_1;

public abstract class ORBInitInfo_3_1Helper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ORBInitInfo_3_1Helper> clazz = ORBInitInfo_3_1Helper.class;
        synchronized (ORBInitInfo_3_1Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/PortableInterceptor/ORBInitInfo_3_1:1.0", "ORBInitInfo_3_1");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ORBInitInfo_3_1 s2) {
        any.insert_Object(s2);
    }

    public static ORBInitInfo_3_1 extract(Any any) {
        return ORBInitInfo_3_1Helper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableInterceptor/ORBInitInfo_3_1:1.0";
    }

    public static ORBInitInfo_3_1 read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, ORBInitInfo_3_1 s2) {
        throw new MARSHAL();
    }

    public static ORBInitInfo_3_1 narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ORBInitInfo_3_1) {
            return (ORBInitInfo_3_1)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ORBInitInfo_3_1 unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ORBInitInfo_3_1) {
            return (ORBInitInfo_3_1)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

