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
import org.omg.DynamicAny.DynUnion;

public abstract class DynUnionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<DynUnionHelper> clazz = DynUnionHelper.class;
        synchronized (DynUnionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/DynamicAny/DynUnion:1.0", "DynUnion");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, DynUnion s2) {
        any.insert_Object(s2);
    }

    public static DynUnion extract(Any any) {
        return DynUnionHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/DynamicAny/DynUnion:1.0";
    }

    public static DynUnion read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, DynUnion s2) {
        throw new MARSHAL();
    }

    public static DynUnion narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof DynUnion) {
            return (DynUnion)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static DynUnion unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof DynUnion) {
            return (DynUnion)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

