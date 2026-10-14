/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.IIOP.Version;

public abstract class VersionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<VersionHelper> clazz = VersionHelper.class;
        synchronized (VersionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(VersionHelper.id(), "Version", new StructMember[]{new StructMember("major", ORB.init().get_primitive_tc(TCKind.from_int(10)), null), new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(10)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, Version s2) {
        any.type(VersionHelper.type());
        VersionHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Version extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            Version version = VersionHelper.read(in);
            return version;
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
        return "IDL:omg.org/IIOP/Version:1.0";
    }

    public static Version read(InputStream in) {
        Version result = new Version();
        result.major = in.read_octet();
        result.minor = in.read_octet();
        return result;
    }

    public static void write(OutputStream out, Version s2) {
        out.write_octet(s2.major);
        out.write_octet(s2.minor);
    }
}

