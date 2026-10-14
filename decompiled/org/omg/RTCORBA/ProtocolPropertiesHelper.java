/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.RTCORBA.ProtocolProperties;

public abstract class ProtocolPropertiesHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ProtocolPropertiesHelper> clazz = ProtocolPropertiesHelper.class;
        synchronized (ProtocolPropertiesHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/RTCORBA/ProtocolProperties:1.0", "ProtocolProperties");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ProtocolProperties s2) {
        any.insert_Object(s2);
    }

    public static ProtocolProperties extract(Any any) {
        return ProtocolPropertiesHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/RTCORBA/ProtocolProperties:1.0";
    }

    public static ProtocolProperties read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, ProtocolProperties s2) {
        throw new MARSHAL();
    }

    public static ProtocolProperties narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ProtocolProperties) {
            return (ProtocolProperties)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ProtocolProperties unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ProtocolProperties) {
            return (ProtocolProperties)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

