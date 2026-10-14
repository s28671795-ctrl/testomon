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
import org.omg.ETF.BufferList;

public abstract class BufferListHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<BufferListHelper> clazz = BufferListHelper.class;
        synchronized (BufferListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/ETF/BufferList:1.0", "BufferList");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, BufferList s2) {
        any.insert_Object(s2);
    }

    public static BufferList extract(Any any) {
        return BufferListHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/ETF/BufferList:1.0";
    }

    public static BufferList read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, BufferList s2) {
        throw new MARSHAL();
    }

    public static BufferList narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BufferList) {
            return (BufferList)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static BufferList unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BufferList) {
            return (BufferList)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

