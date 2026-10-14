/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Security.AuditCombinator;

public abstract class AuditCombinatorHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AuditCombinatorHelper> clazz = AuditCombinatorHelper.class;
        synchronized (AuditCombinatorHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(AuditCombinatorHelper.id(), "AuditCombinator", new String[]{"SecAllSelectors", "SecAnySelector"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AuditCombinator s2) {
        any.type(AuditCombinatorHelper.type());
        AuditCombinatorHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static AuditCombinator extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            AuditCombinator auditCombinator = AuditCombinatorHelper.read(in);
            return auditCombinator;
        }
        finally {
            try {
                in.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Unexpected exception " + e.toString());
            }
        }
    }

    public static String id() {
        return "IDL:omg.org/Security/AuditCombinator:1.0";
    }

    public static AuditCombinator read(InputStream in) {
        return AuditCombinator.from_int(in.read_long());
    }

    public static void write(OutputStream out, AuditCombinator s2) {
        out.write_long(s2.value());
    }
}

