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
import org.omg.Security.AuthenticationStatus;

public abstract class AuthenticationStatusHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AuthenticationStatusHelper> clazz = AuthenticationStatusHelper.class;
        synchronized (AuthenticationStatusHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(AuthenticationStatusHelper.id(), "AuthenticationStatus", new String[]{"SecAuthSuccess", "SecAuthFailure", "SecAuthContinue", "SecAuthExpired"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AuthenticationStatus s2) {
        any.type(AuthenticationStatusHelper.type());
        AuthenticationStatusHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static AuthenticationStatus extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            AuthenticationStatus authenticationStatus = AuthenticationStatusHelper.read(in);
            return authenticationStatus;
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
        return "IDL:omg.org/Security/AuthenticationStatus:1.0";
    }

    public static AuthenticationStatus read(InputStream in) {
        return AuthenticationStatus.from_int(in.read_long());
    }

    public static void write(OutputStream out, AuthenticationStatus s2) {
        out.write_long(s2.value());
    }
}

