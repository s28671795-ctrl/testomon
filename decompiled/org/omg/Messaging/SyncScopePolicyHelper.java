/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Messaging.SyncScopePolicy;

public abstract class SyncScopePolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<SyncScopePolicyHelper> clazz = SyncScopePolicyHelper.class;
        synchronized (SyncScopePolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/Messaging/SyncScopePolicy:1.0", "SyncScopePolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, SyncScopePolicy s2) {
        any.insert_Object(s2);
    }

    public static SyncScopePolicy extract(Any any) {
        return SyncScopePolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/Messaging/SyncScopePolicy:1.0";
    }

    public static SyncScopePolicy read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, SyncScopePolicy s2) {
        throw new MARSHAL();
    }

    public static SyncScopePolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof SyncScopePolicy) {
            return (SyncScopePolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static SyncScopePolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof SyncScopePolicy) {
            return (SyncScopePolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

