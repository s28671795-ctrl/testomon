/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.ETF.Listener;

public abstract class ListenerHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ListenerHelper> clazz = ListenerHelper.class;
        synchronized (ListenerHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/ETF/Listener:1.0", "Listener");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, Listener s2) {
        any.insert_Object(s2);
    }

    public static Listener extract(Any any) {
        return ListenerHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/ETF/Listener:1.0";
    }

    public static Listener read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, Listener s2) {
        throw new MARSHAL();
    }

    public static Listener narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Listener) {
            return (Listener)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static Listener unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Listener) {
            return (Listener)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

