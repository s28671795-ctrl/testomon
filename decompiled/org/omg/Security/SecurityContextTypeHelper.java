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
import org.omg.Security.SecurityContextType;

public abstract class SecurityContextTypeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<SecurityContextTypeHelper> clazz = SecurityContextTypeHelper.class;
        synchronized (SecurityContextTypeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(SecurityContextTypeHelper.id(), "SecurityContextType", new String[]{"SecClientSecurityContext", "SecServerSecurityContext"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, SecurityContextType s2) {
        any.type(SecurityContextTypeHelper.type());
        SecurityContextTypeHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static SecurityContextType extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            SecurityContextType securityContextType = SecurityContextTypeHelper.read(in);
            return securityContextType;
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
        return "IDL:omg.org/Security/SecurityContextType:1.0";
    }

    public static SecurityContextType read(InputStream in) {
        return SecurityContextType.from_int(in.read_long());
    }

    public static void write(OutputStream out, SecurityContextType s2) {
        out.write_long(s2.value());
    }
}

