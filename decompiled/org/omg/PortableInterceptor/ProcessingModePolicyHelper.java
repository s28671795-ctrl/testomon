/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableInterceptor.ProcessingModePolicy;

public abstract class ProcessingModePolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ProcessingModePolicyHelper> clazz = ProcessingModePolicyHelper.class;
        synchronized (ProcessingModePolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/PortableInterceptor/ProcessingModePolicy:1.0", "ProcessingModePolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ProcessingModePolicy s2) {
        any.insert_Object(s2);
    }

    public static ProcessingModePolicy extract(Any any) {
        return ProcessingModePolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableInterceptor/ProcessingModePolicy:1.0";
    }

    public static ProcessingModePolicy read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, ProcessingModePolicy s2) {
        throw new MARSHAL();
    }

    public static ProcessingModePolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ProcessingModePolicy) {
            return (ProcessingModePolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ProcessingModePolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ProcessingModePolicy) {
            return (ProcessingModePolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

