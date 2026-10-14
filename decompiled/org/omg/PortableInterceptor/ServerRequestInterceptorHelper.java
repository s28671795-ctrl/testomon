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
import org.omg.PortableInterceptor.ServerRequestInterceptor;

public abstract class ServerRequestInterceptorHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ServerRequestInterceptorHelper> clazz = ServerRequestInterceptorHelper.class;
        synchronized (ServerRequestInterceptorHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/PortableInterceptor/ServerRequestInterceptor:1.0", "ServerRequestInterceptor");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ServerRequestInterceptor s2) {
        any.insert_Object(s2);
    }

    public static ServerRequestInterceptor extract(Any any) {
        return ServerRequestInterceptorHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableInterceptor/ServerRequestInterceptor:1.0";
    }

    public static ServerRequestInterceptor read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, ServerRequestInterceptor s2) {
        throw new MARSHAL();
    }

    public static ServerRequestInterceptor narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ServerRequestInterceptor) {
            return (ServerRequestInterceptor)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ServerRequestInterceptor unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ServerRequestInterceptor) {
            return (ServerRequestInterceptor)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

