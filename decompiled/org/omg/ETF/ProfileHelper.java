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
import org.omg.ETF.Profile;

public abstract class ProfileHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ProfileHelper> clazz = ProfileHelper.class;
        synchronized (ProfileHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/ETF/Profile:1.0", "Profile");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, Profile s2) {
        any.insert_Object(s2);
    }

    public static Profile extract(Any any) {
        return ProfileHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/ETF/Profile:1.0";
    }

    public static Profile read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, Profile s2) {
        throw new MARSHAL();
    }

    public static Profile narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Profile) {
            return (Profile)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static Profile unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Profile) {
            return (Profile)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

