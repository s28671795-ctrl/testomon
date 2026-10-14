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
import org.omg.Security.InvocationCredentialsType;

public abstract class InvocationCredentialsTypeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InvocationCredentialsTypeHelper> clazz = InvocationCredentialsTypeHelper.class;
        synchronized (InvocationCredentialsTypeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(InvocationCredentialsTypeHelper.id(), "InvocationCredentialsType", new String[]{"SecOwnCredentials", "SecReceivedCredentials"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InvocationCredentialsType s2) {
        any.type(InvocationCredentialsTypeHelper.type());
        InvocationCredentialsTypeHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InvocationCredentialsType extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            InvocationCredentialsType invocationCredentialsType = InvocationCredentialsTypeHelper.read(in);
            return invocationCredentialsType;
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
        return "IDL:omg.org/Security/InvocationCredentialsType:1.0";
    }

    public static InvocationCredentialsType read(InputStream in) {
        return InvocationCredentialsType.from_int(in.read_long());
    }

    public static void write(OutputStream out, InvocationCredentialsType s2) {
        out.write_long(s2.value());
    }
}

