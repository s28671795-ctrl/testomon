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
import org.omg.PortableInterceptor.IORInfo;

public abstract class IORInfoHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<IORInfoHelper> clazz = IORInfoHelper.class;
        synchronized (IORInfoHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/PortableInterceptor/IORInfo:1.0", "IORInfo");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, IORInfo s2) {
        any.insert_Object(s2);
    }

    public static IORInfo extract(Any any) {
        return IORInfoHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableInterceptor/IORInfo:1.0";
    }

    public static IORInfo read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, IORInfo s2) {
        throw new MARSHAL();
    }

    public static IORInfo narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof IORInfo) {
            return (IORInfo)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static IORInfo unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof IORInfo) {
            return (IORInfo)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

