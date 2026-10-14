/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.LifespanPolicyValue;

public abstract class LifespanPolicyValueHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<LifespanPolicyValueHelper> clazz = LifespanPolicyValueHelper.class;
        synchronized (LifespanPolicyValueHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(LifespanPolicyValueHelper.id(), "LifespanPolicyValue", new String[]{"TRANSIENT", "PERSISTENT"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, LifespanPolicyValue s2) {
        any.type(LifespanPolicyValueHelper.type());
        LifespanPolicyValueHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static LifespanPolicyValue extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            LifespanPolicyValue lifespanPolicyValue = LifespanPolicyValueHelper.read(in);
            return lifespanPolicyValue;
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
        return "IDL:omg.org/PortableServer/LifespanPolicyValue:1.0";
    }

    public static LifespanPolicyValue read(InputStream in) {
        return LifespanPolicyValue.from_int(in.read_long());
    }

    public static void write(OutputStream out, LifespanPolicyValue s2) {
        out.write_long(s2.value());
    }
}

