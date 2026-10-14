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
import org.omg.Security.RequiresSupports;

public abstract class RequiresSupportsHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<RequiresSupportsHelper> clazz = RequiresSupportsHelper.class;
        synchronized (RequiresSupportsHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(RequiresSupportsHelper.id(), "RequiresSupports", new String[]{"SecRequires", "SecSupports"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, RequiresSupports s2) {
        any.type(RequiresSupportsHelper.type());
        RequiresSupportsHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static RequiresSupports extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            RequiresSupports requiresSupports = RequiresSupportsHelper.read(in);
            return requiresSupports;
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
        return "IDL:omg.org/Security/RequiresSupports:1.0";
    }

    public static RequiresSupports read(InputStream in) {
        return RequiresSupports.from_int(in.read_long());
    }

    public static void write(OutputStream out, RequiresSupports s2) {
        out.write_long(s2.value());
    }
}

