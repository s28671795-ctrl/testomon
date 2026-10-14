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
import org.omg.Security.SecurityContextState;

public abstract class SecurityContextStateHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<SecurityContextStateHelper> clazz = SecurityContextStateHelper.class;
        synchronized (SecurityContextStateHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(SecurityContextStateHelper.id(), "SecurityContextState", new String[]{"SecContextInitialized", "SecContextContinued", "SecContextClientEstablished", "SecContextEstablished", "SecContextEstablishExpired", "SecContextExpired", "SecContextInvalid"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, SecurityContextState s2) {
        any.type(SecurityContextStateHelper.type());
        SecurityContextStateHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static SecurityContextState extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            SecurityContextState securityContextState = SecurityContextStateHelper.read(in);
            return securityContextState;
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
        return "IDL:omg.org/Security/SecurityContextState:1.0";
    }

    public static SecurityContextState read(InputStream in) {
        return SecurityContextState.from_int(in.read_long());
    }

    public static void write(OutputStream out, SecurityContextState s2) {
        out.write_long(s2.value());
    }
}

