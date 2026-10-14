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
import org.omg.SecurityLevel2.AuditDecision;

public abstract class AuditDecisionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AuditDecisionHelper> clazz = AuditDecisionHelper.class;
        synchronized (AuditDecisionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/SecurityLevel2/AuditDecision:1.0", "AuditDecision");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AuditDecision s2) {
        any.insert_Object(s2);
    }

    public static AuditDecision extract(Any any) {
        return AuditDecisionHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/SecurityLevel2/AuditDecision:1.0";
    }

    public static AuditDecision read(InputStream in) {
        return AuditDecisionHelper.narrow(in.read_Object());
    }

    public static void write(OutputStream _out, AuditDecision s2) {
        _out.write_Object(s2);
    }

    public static AuditDecision narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AuditDecision) {
            return (AuditDecision)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static AuditDecision unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AuditDecision) {
            return (AuditDecision)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

