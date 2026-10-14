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
import org.omg.ETF.ConnectionZeroCopy;

public abstract class ConnectionZeroCopyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ConnectionZeroCopyHelper> clazz = ConnectionZeroCopyHelper.class;
        synchronized (ConnectionZeroCopyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/ETF/ConnectionZeroCopy:1.0", "ConnectionZeroCopy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ConnectionZeroCopy s2) {
        any.insert_Object(s2);
    }

    public static ConnectionZeroCopy extract(Any any) {
        return ConnectionZeroCopyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/ETF/ConnectionZeroCopy:1.0";
    }

    public static ConnectionZeroCopy read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, ConnectionZeroCopy s2) {
        throw new MARSHAL();
    }

    public static ConnectionZeroCopy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ConnectionZeroCopy) {
            return (ConnectionZeroCopy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ConnectionZeroCopy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ConnectionZeroCopy) {
            return (ConnectionZeroCopy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

