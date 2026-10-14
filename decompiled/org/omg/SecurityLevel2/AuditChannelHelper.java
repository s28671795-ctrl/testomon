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
import org.omg.SecurityLevel2.AuditChannel;

public abstract class AuditChannelHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AuditChannelHelper> clazz = AuditChannelHelper.class;
        synchronized (AuditChannelHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/SecurityLevel2/AuditChannel:1.0", "AuditChannel");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AuditChannel s2) {
        any.insert_Object(s2);
    }

    public static AuditChannel extract(Any any) {
        return AuditChannelHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/SecurityLevel2/AuditChannel:1.0";
    }

    public static AuditChannel read(InputStream in) {
        return AuditChannelHelper.narrow(in.read_Object());
    }

    public static void write(OutputStream _out, AuditChannel s2) {
        _out.write_Object(s2);
    }

    public static AuditChannel narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AuditChannel) {
            return (AuditChannel)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static AuditChannel unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AuditChannel) {
            return (AuditChannel)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

