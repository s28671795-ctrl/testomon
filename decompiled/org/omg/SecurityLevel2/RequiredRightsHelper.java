/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.SecurityLevel2.RequiredRights;

public abstract class RequiredRightsHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<RequiredRightsHelper> clazz = RequiredRightsHelper.class;
        synchronized (RequiredRightsHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/SecurityLevel2/RequiredRights:1.0", "RequiredRights");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, RequiredRights s2) {
        any.insert_Object(s2);
    }

    public static RequiredRights extract(Any any) {
        return RequiredRightsHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/SecurityLevel2/RequiredRights:1.0";
    }

    public static RequiredRights read(InputStream in) {
        return RequiredRightsHelper.narrow(in.read_Object());
    }

    public static void write(OutputStream _out, RequiredRights s2) {
        _out.write_Object(s2);
    }

    public static RequiredRights narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof RequiredRights) {
            return (RequiredRights)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static RequiredRights unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof RequiredRights) {
            return (RequiredRights)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

