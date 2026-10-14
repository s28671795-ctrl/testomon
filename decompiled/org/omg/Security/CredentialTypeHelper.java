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
import org.omg.Security.CredentialType;

public abstract class CredentialTypeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CredentialTypeHelper> clazz = CredentialTypeHelper.class;
        synchronized (CredentialTypeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(CredentialTypeHelper.id(), "CredentialType", new String[]{"SecInvocationCredentials", "SecNRCredentials"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, CredentialType s2) {
        any.type(CredentialTypeHelper.type());
        CredentialTypeHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CredentialType extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            CredentialType credentialType = CredentialTypeHelper.read(in);
            return credentialType;
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
        return "IDL:omg.org/Security/CredentialType:1.0";
    }

    public static CredentialType read(InputStream in) {
        return CredentialType.from_int(in.read_long());
    }

    public static void write(OutputStream out, CredentialType s2) {
        out.write_long(s2.value());
    }
}

