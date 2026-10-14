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
import org.omg.PortableServer.ImplicitActivationPolicyValue;

public abstract class ImplicitActivationPolicyValueHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ImplicitActivationPolicyValueHelper> clazz = ImplicitActivationPolicyValueHelper.class;
        synchronized (ImplicitActivationPolicyValueHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(ImplicitActivationPolicyValueHelper.id(), "ImplicitActivationPolicyValue", new String[]{"IMPLICIT_ACTIVATION", "NO_IMPLICIT_ACTIVATION"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ImplicitActivationPolicyValue s2) {
        any.type(ImplicitActivationPolicyValueHelper.type());
        ImplicitActivationPolicyValueHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ImplicitActivationPolicyValue extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ImplicitActivationPolicyValue implicitActivationPolicyValue = ImplicitActivationPolicyValueHelper.read(in);
            return implicitActivationPolicyValue;
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
        return "IDL:omg.org/PortableServer/ImplicitActivationPolicyValue:1.0";
    }

    public static ImplicitActivationPolicyValue read(InputStream in) {
        return ImplicitActivationPolicyValue.from_int(in.read_long());
    }

    public static void write(OutputStream out, ImplicitActivationPolicyValue s2) {
        out.write_long(s2.value());
    }
}

