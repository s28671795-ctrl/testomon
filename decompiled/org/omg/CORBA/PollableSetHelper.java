/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.PollableSet;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class PollableSetHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PollableSetHelper> clazz = PollableSetHelper.class;
        synchronized (PollableSetHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/CORBA/PollableSet:1.0", "PollableSet");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, PollableSet s2) {
        any.insert_Object(s2);
    }

    public static PollableSet extract(Any any) {
        return PollableSetHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/PollableSet:1.0";
    }

    public static PollableSet read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, PollableSet s2) {
        throw new MARSHAL();
    }

    public static PollableSet narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof PollableSet) {
            return (PollableSet)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static PollableSet unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof PollableSet) {
            return (PollableSet)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

