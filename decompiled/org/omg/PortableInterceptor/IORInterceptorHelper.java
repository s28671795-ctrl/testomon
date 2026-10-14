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
import org.omg.PortableInterceptor.IORInterceptor;

public abstract class IORInterceptorHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<IORInterceptorHelper> clazz = IORInterceptorHelper.class;
        synchronized (IORInterceptorHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/PortableInterceptor/IORInterceptor:1.0", "IORInterceptor");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, IORInterceptor s2) {
        any.insert_Object(s2);
    }

    public static IORInterceptor extract(Any any) {
        return IORInterceptorHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableInterceptor/IORInterceptor:1.0";
    }

    public static IORInterceptor read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, IORInterceptor s2) {
        throw new MARSHAL();
    }

    public static IORInterceptor narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof IORInterceptor) {
            return (IORInterceptor)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static IORInterceptor unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof IORInterceptor) {
            return (IORInterceptor)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

