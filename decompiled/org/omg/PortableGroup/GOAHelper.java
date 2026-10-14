/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableGroup.GOA;

public abstract class GOAHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<GOAHelper> clazz = GOAHelper.class;
        synchronized (GOAHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/PortableGroup/GOA:1.0", "GOA");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, GOA s2) {
        any.insert_Object(s2);
    }

    public static GOA extract(Any any) {
        return GOAHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableGroup/GOA:1.0";
    }

    public static GOA read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, GOA s2) {
        throw new MARSHAL();
    }

    public static GOA narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof GOA) {
            return (GOA)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static GOA unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof GOA) {
            return (GOA)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

