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
import org.omg.ETF.Factories;

public abstract class FactoriesHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<FactoriesHelper> clazz = FactoriesHelper.class;
        synchronized (FactoriesHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/ETF/Factories:1.0", "Factories");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, Factories s2) {
        any.insert_Object(s2);
    }

    public static Factories extract(Any any) {
        return FactoriesHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/ETF/Factories:1.0";
    }

    public static Factories read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, Factories s2) {
        throw new MARSHAL();
    }

    public static Factories narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Factories) {
            return (Factories)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static Factories unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Factories) {
            return (Factories)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

