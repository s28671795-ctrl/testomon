/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.DynamicAny.DynStruct;

public abstract class DynStructHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<DynStructHelper> clazz = DynStructHelper.class;
        synchronized (DynStructHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/DynamicAny/DynStruct:1.0", "DynStruct");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, DynStruct s2) {
        any.insert_Object(s2);
    }

    public static DynStruct extract(Any any) {
        return DynStructHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/DynamicAny/DynStruct:1.0";
    }

    public static DynStruct read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, DynStruct s2) {
        throw new MARSHAL();
    }

    public static DynStruct narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof DynStruct) {
            return (DynStruct)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static DynStruct unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof DynStruct) {
            return (DynStruct)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

