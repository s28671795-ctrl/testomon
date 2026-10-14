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
import org.omg.RTCORBA.Current;

public abstract class CurrentHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CurrentHelper> clazz = CurrentHelper.class;
        synchronized (CurrentHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/RTCORBA/Current:1.0", "Current");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, Current s2) {
        any.insert_Object(s2);
    }

    public static Current extract(Any any) {
        return CurrentHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/RTCORBA/Current:1.0";
    }

    public static Current read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, Current s2) {
        throw new MARSHAL();
    }

    public static Current narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Current) {
            return (Current)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static Current unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Current) {
            return (Current)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

